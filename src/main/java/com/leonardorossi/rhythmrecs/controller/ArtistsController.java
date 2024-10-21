package com.leonardorossi.rhythmrecs.controller;

import com.leonardorossi.rhythmrecs.clients.spotify.AuthSpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.ArtistResponseDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginRequestDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginResponseDto;
import com.leonardorossi.rhythmrecs.dtos.FavoritesArtistsDto;
import com.leonardorossi.rhythmrecs.services.ArtistsService;
import com.leonardorossi.rhythmrecs.services.SpotifyAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArtistsController {
    
    private final SpotifyAuthService spotifyAuthService;
    
    private final ArtistsService artistsService;
    
    @GetMapping
    public ResponseEntity<List<ArtistResponseDto>> getArtistsBasedOnGenre(@RequestParam Long id) throws BadRequestException {
        try {
            return ResponseEntity.ok(artistsService.getGenresByPerson(id,
                spotifyAuthService.getToken()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException(e);
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ArtistResponseDto>> searchArtists(
        @RequestParam String query
    ) throws BadRequestException {
        try {
            return ResponseEntity.ok(artistsService.searchArtists(query,
                spotifyAuthService.getToken()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity<Void> register(
        @RequestBody List<FavoritesArtistsDto> dto,
        @RequestParam long personId
    ) throws BadRequestException {
        try {
            artistsService.register(dto, personId);
            
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
    
}
