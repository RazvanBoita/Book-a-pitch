package com.book_a_pitch.bapAPI.services.interfaces;

import com.book_a_pitch.bapAPI.common.dtos.BookingRequest;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.domain.Booking;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IBookingService {
    Result<Boolean> createBooking(BookingRequest bookingRequest, Authentication authentication);
    Result<List<Booking>> getBookingsForPitch(Long pitchId);

    Result<List<Booking>> getBookingsForUser(Authentication authentication);
}
