package com.book_a_pitch.bapAPI.services.interfaces;

import com.book_a_pitch.bapAPI.common.dtos.PitchDto;
import com.book_a_pitch.bapAPI.common.responses.Result;

import java.util.List;

public interface IPitchService {
    Result<List<PitchDto>> getAllPitches();
    Result<PitchDto> getPitchById(Long id);
}
