package com.leonardorossi.rhythmrecs.controller;

import com.leonardorossi.rhythmrecs.clients.spotify.AuthSpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginRequestDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginResponseDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.TrackDto;
import com.leonardorossi.rhythmrecs.services.FavoritesMusicsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TracksController {
    
    private final FavoritesMusicsService service;
    
    private final AuthSpotifyClient authSpotifyClient;
    
    @GetMapping
    public ResponseEntity<List<TrackDto>> getTracks(
        @RequestParam long personId
    ) {
        try {
            String token = this.getToken();
            return ResponseEntity
                .ok(service.getMusicsByFavoritesArtistsFromPerson(personId, token));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<TrackDto>> search(
        @RequestParam String query
    ) {
        try {
            String token = this.getToken();
            
            return ResponseEntity.ok(service.search(query, token));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
    
    private String getToken() {
        LoginRequestDto request = LoginRequestDto.buildObject();
        LoginResponseDto loginResponseDto = authSpotifyClient.login(request);
        
        return "Bearer " + loginResponseDto.accessToken();
    }
    
}
