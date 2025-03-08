package com.book_a_pitch.bapAPI.common.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PitchDto {
    private String name;
    private String location;
    private Double pricePerHour;
}
