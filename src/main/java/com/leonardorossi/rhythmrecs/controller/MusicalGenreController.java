package com.leonardorossi.rhythmrecs.controller;

import com.leonardorossi.rhythmrecs.clients.spotify.SpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.GenreResponseDto;
import com.leonardorossi.rhythmrecs.dtos.FavoritesGenresDto;
import com.leonardorossi.rhythmrecs.services.FavoritesGenresService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MusicalGenreController {
    
    private final SpotifyClient spotifyClient;
    private final FavoritesGenresService favoritesGenresService;
    
    @GetMapping
    public ResponseEntity<List<String>> getGenres(@RequestParam String spotifyToken) {
        String bearerToken = "Bearer " + spotifyToken;
        
        GenreResponseDto genreDto = spotifyClient.getAvailableGenreSeeds(bearerToken);
        
        return ResponseEntity.ok(genreDto.getGenres());
    }
    
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody FavoritesGenresDto dto) throws BadRequestException {
        try {
            favoritesGenresService.register(dto.genres(), dto.personId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e);
            throw new BadRequestException(e);
        }
    }
    
}
