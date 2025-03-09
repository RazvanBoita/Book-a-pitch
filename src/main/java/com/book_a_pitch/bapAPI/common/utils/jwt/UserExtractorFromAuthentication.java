package com.book_a_pitch.bapAPI.common.utils.jwt;

import com.book_a_pitch.bapAPI.domain.User;
import com.book_a_pitch.bapAPI.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserExtractorFromAuthentication {
    private final UserRepository userRepository;

    public UserExtractorFromAuthentication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User extractUser(Authentication authentication){
        var email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No such email in the database"));
    }
}
