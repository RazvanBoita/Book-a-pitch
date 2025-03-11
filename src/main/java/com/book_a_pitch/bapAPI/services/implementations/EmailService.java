package com.book_a_pitch.bapAPI.services.implementations;

import com.book_a_pitch.bapAPI.domain.Booking;
import com.book_a_pitch.bapAPI.services.interfaces.IEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@yourcompany.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendBookingConfirmation(String to, Booking booking) {
        String subject = "Booking Confirmation - Ref: " + booking.getBookingId();

        // Create a formatted message with booking details
        String message = String.format(
                "Draga utilizator,\n\n" +
                        "Rezervarea dvs a fost confirmata!\n\n" +
                        "Detalii:\n" +
                        "Locatia terenului: %s\n" +
                        "Data: %s\n" +
                        "Interval orar: %s - %s\n\n" +
                        "Multumim pentru alegerea serviciului nostru.\n\n" +
                        "Te asteptam cu drag la fotbal,\n" +
                        "Book-a-pitch Iasi",
                booking.getPitch().getName(),
                booking.getStartTime().toLocalDate(),
                booking.getStartTime().toLocalTime(),
                booking.getEndTime().toLocalTime()
        );

        sendSimpleMessage(to, subject, message);
    }
}
