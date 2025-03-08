package com.book_a_pitch.bapAPI.common.mappers;

import com.book_a_pitch.bapAPI.common.dtos.PitchDto;
import com.book_a_pitch.bapAPI.domain.Pitch;

public class PitchMapper {
    public static PitchDto mapPitchToDto(Pitch pitch){
        return new PitchDto(pitch.getName(), pitch.getLocation(), pitch.getPricePerHour());
    }

    public static Pitch mapDtoToPitch(PitchDto pitchDto){
        return null;
    }
}
