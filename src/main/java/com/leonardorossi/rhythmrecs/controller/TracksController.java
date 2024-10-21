package com.leonardorossi.rhythmrecs.controller;

import com.leonardorossi.rhythmrecs.clients.spotify.AuthSpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginRequestDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginResponseDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.TrackDto;
import com.leonardorossi.rhythmrecs.dtos.FavoritesTracksDto;
import com.leonardorossi.rhythmrecs.services.FavoritesMusicsService;
import com.leonardorossi.rhythmrecs.services.SpotifyAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TracksController {
    
    private final FavoritesMusicsService service;
    
    private final SpotifyAuthService spotifyAuthService;
    
    @GetMapping
    public ResponseEntity<List<TrackDto>> getTracks(
        @RequestParam long personId
    ) {
        try {
            return ResponseEntity
                .ok(service.getMusicsByFavoritesArtistsFromPerson(personId,
                    spotifyAuthService.getToken()));
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
            return ResponseEntity.ok(service.search(query, spotifyAuthService.getToken()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity<Void> register(
        @RequestBody List<FavoritesTracksDto> dto,
        @RequestParam long personId
    ) throws BadRequestException {
        try {
            service.register(dto, personId);
            
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
}
