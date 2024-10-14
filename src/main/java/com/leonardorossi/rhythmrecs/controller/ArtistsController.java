package com.leonardorossi.rhythmrecs.controller;

import com.leonardorossi.rhythmrecs.clients.spotify.AuthSpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.ArtistResponseDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginRequestDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginResponseDto;
import com.leonardorossi.rhythmrecs.dtos.FavoritesArtistsDto;
import com.leonardorossi.rhythmrecs.services.ArtistsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ArtistsController {
    
    private final AuthSpotifyClient authSpotifyClient;
    
    private final ArtistsService artistsService;
    
    @GetMapping
    public ResponseEntity<List<ArtistResponseDto>> getArtistsBasedOnGenre(@RequestParam Long id) throws BadRequestException {
        try {
            String token = getToken();
            
            return ResponseEntity.ok(artistsService.getGenresByPerson(id, token));
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
            String token = getToken();
            
            return ResponseEntity.ok(artistsService.searchArtists(query, token));
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
    
    private String getToken() {
        LoginRequestDto request = LoginRequestDto.buildObject();
        LoginResponseDto loginResponseDto = authSpotifyClient.login(request);
        
        return "Bearer " + loginResponseDto.accessToken();
    }
    
}
