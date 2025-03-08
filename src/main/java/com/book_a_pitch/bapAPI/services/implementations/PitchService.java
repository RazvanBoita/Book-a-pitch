package com.book_a_pitch.bapAPI.services.implementations;

import com.book_a_pitch.bapAPI.common.dtos.PitchDto;
import com.book_a_pitch.bapAPI.common.mappers.PitchMapper;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.repositories.PitchRepository;
import com.book_a_pitch.bapAPI.services.interfaces.IPitchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PitchService implements IPitchService {
    private final PitchRepository pitchRepository;

    public PitchService(PitchRepository pitchRepository) {
        this.pitchRepository = pitchRepository;
    }

    @Override
    public Result<List<PitchDto>> getAllPitches() {
        try{
            var allPitches =  pitchRepository.findAll();
            var mappedPitches =  allPitches.stream()
                    .map(PitchMapper::mapPitchToDto)
                    .toList();
            return new Result<>(mappedPitches);
        }
        catch (Exception e){
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<PitchDto> getPitchById(Long id) {
        return null;
    }
}
