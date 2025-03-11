package com.book_a_pitch.bapAPI.common.errors;

public class BookingErrors {
    public static String StartHourInPast = "Booking can't be in the past!";
    public static String EndTimeBeforeStartTime = "Booking can't end before it starts!";
    public static String InvalidDuration = "Booking has to last exactly 2 hours!";
    public static String PitchDoesntExist = "There is no pitch with the given id!";
    public static String BookingHasConflict = "The time slot is already taken!";
    public static String ViolationOfPitchHours = "Please respect the opening and closing hours!";
    public static String InvalidHour = "Booking has to start at Hour:00(mins)!";
    public static String InvalidGivenDays = "You can only see the bookings from today until next week!";
    public static String InvalidBookingForPitch = "Please check the available slots for the pitch!";

}
