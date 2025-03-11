package com.book_a_pitch.bapAPI.services.implementations;

import com.book_a_pitch.bapAPI.common.dtos.BookingRequest;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.common.utils.jwt.UserExtractorFromAuthentication;
import com.book_a_pitch.bapAPI.domain.Booking;
import com.book_a_pitch.bapAPI.domain.TimeSlot;
import com.book_a_pitch.bapAPI.repositories.BookingRepository;
import com.book_a_pitch.bapAPI.repositories.PitchRepository;
import com.book_a_pitch.bapAPI.services.interfaces.IBookingService;
import com.book_a_pitch.bapAPI.services.interfaces.IEmailService;
import com.book_a_pitch.bapAPI.services.interfaces.IPaymentService;
import com.book_a_pitch.bapAPI.validators.BookingValidator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final PitchRepository pitchRepository;
    private final BookingValidator bookingValidator;
    private final UserExtractorFromAuthentication userExtractor;
    private final IEmailService emailService;

    public BookingService(BookingRepository bookingRepository, PitchRepository pitchRepository,
                          BookingValidator bookingValidator, UserExtractorFromAuthentication userExtractor,
                          IEmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.pitchRepository = pitchRepository;
        this.bookingValidator = bookingValidator;
        this.userExtractor = userExtractor;
        this.emailService = emailService;
    }

    @Override
    public Result<Boolean> createBooking(BookingRequest bookingRequest, Authentication authentication) {
        try{
            bookingValidator.validatePitch(bookingRequest.getPitchId());
            bookingValidator.validateBookingTimes(bookingRequest.getStartTime(), bookingRequest.getEndTime());
            bookingValidator.checkPitchHours(bookingRequest);
            bookingValidator.checkConflicts(bookingRequest);
            bookingValidator.validateBookingForGivenPitch(bookingRequest);

            var userAccount = userExtractor.extractUser(authentication);
            var pitch = pitchRepository.findById(bookingRequest.getPitchId());

            //vezi cu payment ce faci

            var booking = new Booking();
            //noinspection OptionalGetWithoutIsPresent
            booking.setPitch(pitch.get());
            booking.setEndTime(bookingRequest.getEndTime());
            booking.setStartTime(bookingRequest.getStartTime());
            booking.setUser(userAccount);

            bookingRepository.save(booking);
            emailService.sendBookingConfirmation(userAccount.getEmail(), booking);
            return new Result<>(true);
        }
        catch (Exception e){
            System.out.println("Got error message in service: " + e.getMessage());
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Booking>> getBookingsForPitch(Long pitchId) {
        try{
            var bookings = bookingRepository.findByPitchPitchId(pitchId);
            return new Result<>(bookings);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Booking>> getBookingsForUser(Authentication authentication) {
        try{
            var userAccount = userExtractor.extractUser(authentication);
            var bookings = bookingRepository.findByUserUserId(userAccount.getUserId());
            return new Result<>(bookings);
        }
        catch (Exception e){
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Booking>> getBookingsForDayAndPitch(int daysFromToday, Long pitchId) {
        try{
            bookingValidator.validateDaysFromToday(daysFromToday);
            bookingValidator.validatePitch(pitchId);

            var bookingsByPitch = bookingRepository.findByPitchPitchId(pitchId);
            if(bookingsByPitch.isEmpty())
                return new Result<>(new ArrayList<>());

            LocalDate today = LocalDate.now();
            LocalDate targetDate = today.plusDays(daysFromToday);

            var bookingsByDayAndPitch = bookingsByPitch
                    .stream()
                    .filter(booking -> booking.getStartTime().toLocalDate().equals(targetDate))
                    .toList();

            return new Result<>(bookingsByDayAndPitch);
        }
        catch (Exception e){
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<TimeSlot>> getFreeTimeSlotsForDayAndPitch(int daysFromToday, Long pitch_id) {
        try {
            var bookingsFromDayAndPitchResult = getBookingsForDayAndPitch(daysFromToday, pitch_id);
            if (!bookingsFromDayAndPitchResult.isSuccess()) {
                throw new Exception(bookingsFromDayAndPitchResult.getErrorMessage());
            }
            var pitch = pitchRepository.findById(pitch_id);
            var openingHour = pitch.get().getOpeningHour();
            var closingHour = pitch.get().getClosingHour();

            var bookingsFromDayAndPitch = new ArrayList<>(bookingsFromDayAndPitchResult.getData());
            bookingsFromDayAndPitch.sort(Comparator.comparing(Booking::getStartTime));

            List<TimeSlot> freeTimeSlots = new ArrayList<>();
            LocalTime currentSlotStart = openingHour;

            // Modified loop condition to stop before reaching closingHour
            while (currentSlotStart.plusHours(2).isAfter(currentSlotStart) &&
                    currentSlotStart.plusHours(2).compareTo(closingHour) <= 0) {
                LocalTime currentSlotEnd = currentSlotStart.plusHours(2);

                LocalTime finalCurrentSlotStart = currentSlotStart;
                boolean isBooked = bookingsFromDayAndPitch.stream().anyMatch(booking ->
                        (booking.getStartTime().toLocalTime().isBefore(currentSlotEnd) &&
                                booking.getEndTime().toLocalTime().isAfter(finalCurrentSlotStart))
                );

                if (!isBooked) {
                    freeTimeSlots.add(new TimeSlot(currentSlotStart, currentSlotEnd, "Available"));
                }

                currentSlotStart = currentSlotEnd;
            }

            return new Result<>(freeTimeSlots);
        }
        catch (Exception e) {
            return new Result<>(e.getMessage());
        }
    }


}
