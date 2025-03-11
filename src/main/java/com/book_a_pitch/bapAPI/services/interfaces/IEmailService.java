package com.book_a_pitch.bapAPI.services.interfaces;

import com.book_a_pitch.bapAPI.domain.Booking;

public interface IEmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendBookingConfirmation(String to, Booking booking);
}
