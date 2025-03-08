package com.book_a_pitch.bapAPI.common.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingRequest {
    private Long pitchId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
