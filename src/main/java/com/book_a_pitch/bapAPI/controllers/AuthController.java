package com.book_a_pitch.bapAPI.controllers;

import com.book_a_pitch.bapAPI.common.dtos.AuthDto;
import com.book_a_pitch.bapAPI.services.implementations.CustomUserDetailsService;
import com.book_a_pitch.bapAPI.common.utils.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDto authDto){
        System.out.println("Got into login");
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
            );
        }
        catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthDto authDto){
        if (userDetailsService.userExists(authDto.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        userDetailsService.createUser(authDto);
        return ResponseEntity.ok("User registered successfully");
    }

}
