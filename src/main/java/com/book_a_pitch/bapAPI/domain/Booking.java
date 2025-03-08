package com.book_a_pitch.bapAPI.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookingId;

    @ManyToOne
    @JoinColumn(name = "pitchId")
    private Pitch pitch;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
