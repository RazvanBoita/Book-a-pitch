package com.book_a_pitch.bapAPI.controllers;


import com.book_a_pitch.bapAPI.common.dtos.PitchDto;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.repositories.UserRepository;
import com.book_a_pitch.bapAPI.services.interfaces.IPitchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pitches")
public class PitchController {

    private final IPitchService pitchService;
    private final UserRepository userRepository;

    public PitchController(IPitchService pitchService, UserRepository userRepository) {
        this.pitchService = pitchService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Result<List<PitchDto>>> get() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        var email = authentication.getName();
//        System.out.println("This request was done by user with email: " + email);
//        var uid = userRepository.findByEmail(email).get().getUserId();
//        System.out.println("This request was done by user with uuid: " + uid);

        var response = pitchService.getAllPitches();
        return new ResponseEntity<>(response,
                response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
