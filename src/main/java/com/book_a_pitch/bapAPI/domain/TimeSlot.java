package com.book_a_pitch.bapAPI.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private String details = "";
}
