package com.book_a_pitch.bapAPI.validators;

import com.book_a_pitch.bapAPI.common.dtos.BookingRequest;
import com.book_a_pitch.bapAPI.common.errors.BookingErrors;
import com.book_a_pitch.bapAPI.common.exceptions.booking.InvalidBookingException;
import com.book_a_pitch.bapAPI.domain.Booking;
import com.book_a_pitch.bapAPI.repositories.BookingRepository;
import com.book_a_pitch.bapAPI.repositories.PitchRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingValidator {
    private final PitchRepository pitchRepository;
    private final BookingRepository bookingRepository;

    public BookingValidator(PitchRepository pitchRepository, BookingRepository bookingRepository) {
        this.pitchRepository = pitchRepository;
        this.bookingRepository = bookingRepository;
    }

    public void validateBookingTimes(LocalDateTime startTime, LocalDateTime endTime){
        LocalDateTime now = LocalDateTime.now();
        if(startTime.isBefore(now)){
            throw new InvalidBookingException(BookingErrors.StartHourInPast);
        }

        if(endTime.isBefore(startTime) || endTime.equals(startTime)){
            throw new InvalidBookingException(BookingErrors.EndTimeBeforeStartTime);
        }


        long minutes = Duration.between(startTime, endTime).toMinutes();
        if (minutes != 120) {
            throw new InvalidBookingException(BookingErrors.InvalidDuration);
        }

        if(startTime.getMinute() != 0){
            throw new InvalidBookingException(BookingErrors.InvalidHour);
        }
    }

    public void validateBookingForGivenPitch(BookingRequest bookingRequest){
        var pitch = pitchRepository.findById(bookingRequest.getPitchId());
        if (pitch.isEmpty()){
            throw new InvalidBookingException(BookingErrors.PitchDoesntExist);
        }

        boolean isOpeningHourEven = (pitch.get().getOpeningHour().getHour() % 2 == 0);
        boolean isBookingStartEven = (bookingRequest.getStartTime().getHour() % 2 == 0);

        if(isOpeningHourEven != isBookingStartEven) {
            throw new InvalidBookingException(BookingErrors.InvalidBookingForPitch);
        }
    }

    public void validatePitch(Long id){
        var pitch = pitchRepository.findById(id);
        if (pitch.isEmpty()){
            throw new InvalidBookingException(BookingErrors.PitchDoesntExist);
        }
    }

    public void checkConflicts(BookingRequest bookingRequest){
        var pitch = pitchRepository.findById(bookingRequest.getPitchId());

        List<Booking> existingBookings = bookingRepository.findByPitchAndTimeOverlap(
                pitch.get().getPitchId(), bookingRequest.getStartTime(), bookingRequest.getEndTime());

        if(!existingBookings.isEmpty()){
            throw new InvalidBookingException(BookingErrors.BookingHasConflict);
        }
    }

    public void checkPitchHours(BookingRequest bookingRequest){
        var pitch = pitchRepository.findById(bookingRequest.getPitchId()).get();
        if(
                pitch.getClosingHour().isBefore(bookingRequest.getStartTime().toLocalTime()) ||
                pitch.getOpeningHour().isAfter(bookingRequest.getStartTime().toLocalTime())
        ){
            throw new InvalidBookingException(BookingErrors.ViolationOfPitchHours);
        }
    }

    public void validateDaysFromToday(int daysFromToday){
        if(daysFromToday < 0 || daysFromToday > 7){
            throw new InvalidBookingException(BookingErrors.InvalidGivenDays);
        }
    }

}
