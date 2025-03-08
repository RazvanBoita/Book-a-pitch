package com.book_a_pitch.bapAPI.common.exceptions.booking;

public class InvalidBookingException extends RuntimeException{
    public InvalidBookingException(String message) {
        super(message);
    }
}