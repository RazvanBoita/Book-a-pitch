package com.book_a_pitch.bapAPI.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pitches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitchId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double pricePerHour;

    @OneToMany(mappedBy = "pitch")
    private List<Booking> bookingList = new ArrayList<>();

    @Column(nullable = false)
    private LocalTime openingHour;
    //consideram openingHour ca fiind prima ora la care se poate face rezervare

    @Column(nullable = false)
    private LocalTime closingHour;
    //consideram closingHour ca fiind ultima ora la care se poate face rezervare
}
