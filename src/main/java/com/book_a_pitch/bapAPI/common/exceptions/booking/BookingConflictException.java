package com.book_a_pitch.bapAPI.common.exceptions.booking;

public class BookingConflictException extends RuntimeException{
    public BookingConflictException(String message) {
        super(message);
    }
}
