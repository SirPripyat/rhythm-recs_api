package com.leonardorossi.rhythmrecs.controller;

import com.leonardorossi.rhythmrecs.clients.spotify.AuthSpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginRequestDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spotify/api")
@RequiredArgsConstructor
@Transactional
@Log4j2
public class AuthController {
    
    private final AuthSpotifyClient authSpotifyClient;
    
    @GetMapping("/token")
    public ResponseEntity<LoginResponseDto> getToken() throws BadRequestException {
        try {
            LoginRequestDto request = LoginRequestDto.buildObject();
            return ResponseEntity.ok(authSpotifyClient.login(request));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
        
    }
}



