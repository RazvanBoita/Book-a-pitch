package com.book_a_pitch.bapAPI.services.interfaces;

import com.book_a_pitch.bapAPI.common.dtos.BookingRequest;
import com.book_a_pitch.bapAPI.common.responses.Result;

public interface IBookingService {
    Result<Boolean> createBooking(BookingRequest bookingRequest);
}
