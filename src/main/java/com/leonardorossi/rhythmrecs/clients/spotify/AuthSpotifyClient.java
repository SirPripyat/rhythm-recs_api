package com.leonardorossi.rhythmrecs.clients.spotify;

import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginRequestDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "AuthSpotifyClient",
    url = "https://accounts.spotify.com"
)
public interface AuthSpotifyClient {
    
    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    LoginResponseDto login(@RequestBody LoginRequestDto dto);
    
}