package com.book_a_pitch.bapAPI.common.errors;

public class BookingErrors {
    public static String StartHourInPast = "Booking can't be in the past!";
    public static String EndTimeBeforeStartTime = "Booking can't end before it starts!";
    public static String InvalidDuration = "Booking has to last 1 or 2 hours!";
    public static String PitchDoesntExist = "There is no pitch with the given id!";
    public static String BookingHasConflict = "The time slot is already taken!";
    public static String ViolationOfPitchHours = "Please respect the opening and closing hours!";
    public static String InvalidHour = "Booking has to start at Hour:30(mins) or Hour:00(mins)!";

}
