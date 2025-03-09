package com.book_a_pitch.bapAPI.services.implementations;

import com.book_a_pitch.bapAPI.common.dtos.BookingRequest;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.common.utils.jwt.UserExtractorFromAuthentication;
import com.book_a_pitch.bapAPI.domain.Booking;
import com.book_a_pitch.bapAPI.repositories.BookingRepository;
import com.book_a_pitch.bapAPI.repositories.PitchRepository;
import com.book_a_pitch.bapAPI.services.interfaces.IBookingService;
import com.book_a_pitch.bapAPI.validators.BookingValidator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final PitchRepository pitchRepository;
    private final BookingValidator bookingValidator;
    private final UserExtractorFromAuthentication userExtractor;

    public BookingService(BookingRepository bookingRepository, PitchRepository pitchRepository, BookingValidator bookingValidator, UserExtractorFromAuthentication userExtractor) {
        this.bookingRepository = bookingRepository;
        this.pitchRepository = pitchRepository;
        this.bookingValidator = bookingValidator;
        this.userExtractor = userExtractor;
    }

    @Override
    public Result<Boolean> createBooking(BookingRequest bookingRequest, Authentication authentication) {
        try{
            bookingValidator.validatePitch(bookingRequest.getPitchId());
            bookingValidator.validateBookingTimes(bookingRequest.getStartTime(), bookingRequest.getEndTime());
            bookingValidator.checkPitchHours(bookingRequest);
            bookingValidator.checkConflicts(bookingRequest);

            var userAccount = userExtractor.extractUser(authentication);
            var pitch = pitchRepository.findById(bookingRequest.getPitchId());
            var booking = new Booking();
            //noinspection OptionalGetWithoutIsPresent
            booking.setPitch(pitch.get());
            booking.setEndTime(bookingRequest.getEndTime());
            booking.setStartTime(bookingRequest.getStartTime());
            booking.setUser(userAccount);

            bookingRepository.save(booking);
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
}
