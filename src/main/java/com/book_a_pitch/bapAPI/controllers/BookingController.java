package com.book_a_pitch.bapAPI.controllers;

import com.book_a_pitch.bapAPI.common.dtos.BookingRequest;
import com.book_a_pitch.bapAPI.services.interfaces.IBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest bookingRequest){
        var response = bookingService.createBooking(bookingRequest);
        return response.isSuccess()
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }
}
