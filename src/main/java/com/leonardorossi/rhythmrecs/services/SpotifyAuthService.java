package com.leonardorossi.rhythmrecs.services;

import com.leonardorossi.rhythmrecs.clients.spotify.AuthSpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginRequestDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpotifyAuthService {
    
    private final AuthSpotifyClient authSpotifyClient;
    
    public String getToken() {
        LoginRequestDto request = LoginRequestDto.buildObject();
        LoginResponseDto loginResponseDto = this.authSpotifyClient.login(request);
        return "Bearer " + loginResponseDto.accessToken();
    }
    
}
