package com.book_a_pitch.bapAPI.controllers;

import com.book_a_pitch.bapAPI.common.dtos.BookingRequest;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.domain.Booking;
import com.book_a_pitch.bapAPI.domain.TimeSlot;
import com.book_a_pitch.bapAPI.services.interfaces.IBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest bookingRequest){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var response = bookingService.createBooking(bookingRequest, authentication);
        return response.isSuccess()
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{pitchId}")
    public ResponseEntity<Result<List<Booking>>> getBookingsForPitch(@PathVariable Long pitchId){
        var response = bookingService.getBookingsForPitch(pitchId);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Result<List<Booking>>> getBookingsForUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var response = bookingService.getBookingsForUser(authentication);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/schedule")
    public ResponseEntity<Result<List<Booking>>> getBookingsForDayAndPitch(@RequestParam int days, @RequestParam Long pitchId){

        var response = bookingService.getBookingsForDayAndPitch(days, pitchId);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/schedule/free")
    public ResponseEntity<Result<List<TimeSlot>>> getTimeSlotsForDayAndPitch(@RequestParam int days, @RequestParam Long pitchId){
        System.out.println("Days retrieved: " + days);
        System.out.println("Pitch Id retrieved: " + pitchId);
        var response = bookingService.getFreeTimeSlotsForDayAndPitch(days, pitchId);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        System.out.println(response.getErrorMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
