package com.book_a_pitch.bapAPI.services.implementations;

import com.book_a_pitch.bapAPI.common.dtos.BookingRequest;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.domain.Booking;
import com.book_a_pitch.bapAPI.repositories.BookingRepository;
import com.book_a_pitch.bapAPI.repositories.PitchRepository;
import com.book_a_pitch.bapAPI.services.interfaces.IBookingService;
import com.book_a_pitch.bapAPI.validators.BookingValidator;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final PitchRepository pitchRepository;
    private final BookingValidator bookingValidator;

    public BookingService(BookingRepository bookingRepository, PitchRepository pitchRepository, BookingValidator bookingValidator) {
        this.bookingRepository = bookingRepository;
        this.pitchRepository = pitchRepository;
        this.bookingValidator = bookingValidator;
    }

    @Override
    public Result<Boolean> createBooking(BookingRequest bookingRequest) {
        try{
            bookingValidator.validatePitch(bookingRequest.getPitchId());
            bookingValidator.validateBookingTimes(bookingRequest.getStartTime(), bookingRequest.getEndTime());
            bookingValidator.checkPitchHours(bookingRequest);
            bookingValidator.checkConflicts(bookingRequest);

            var pitch = pitchRepository.findById(bookingRequest.getPitchId());
            var booking = new Booking();
            //noinspection OptionalGetWithoutIsPresent
            booking.setPitch(pitch.get());
            booking.setEndTime(bookingRequest.getEndTime());
            booking.setStartTime(bookingRequest.getStartTime());
            bookingRepository.save(booking);
            return new Result<>(true);
        }
        catch (Exception e){
            System.out.println("Got error message in service: " + e.getMessage());
            return new Result<>(e.getMessage());
        }
    }
}
