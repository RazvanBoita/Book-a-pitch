package com.book_a_pitch.bapAPI.controllers;


import com.book_a_pitch.bapAPI.common.dtos.PitchDto;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.services.interfaces.IPitchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pitches")
public class PitchController {

    private final IPitchService pitchService;

    public PitchController(IPitchService pitchService) {
        this.pitchService = pitchService;
    }

    @GetMapping
    public ResponseEntity<Result<List<PitchDto>>> get() {
        var response = pitchService.getAllPitches();
        return new ResponseEntity<>(response,
                response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
