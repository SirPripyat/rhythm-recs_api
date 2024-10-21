package com.leonardorossi.rhythmrecs.controller;

import com.leonardorossi.rhythmrecs.clients.spotify.AuthSpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.AudioFeaturesDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginRequestDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.LoginResponseDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.TrackDto;
import com.leonardorossi.rhythmrecs.dtos.RegisterRecommendationDto;
import com.leonardorossi.rhythmrecs.services.RecommendationService;
import com.leonardorossi.rhythmrecs.services.SpotifyAuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecommendationController {
    
    private final RecommendationService recommendationService;
    
    @GetMapping("/{id}")
    public ResponseEntity<List<TrackDto>> getRecommendation(
        @PathVariable long id
    ) {
        try {
            return ResponseEntity.ok(recommendationService.execute(id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping()
    public ResponseEntity<Void> register(
        @RequestParam long id,
        @RequestBody List<RegisterRecommendationDto> dto
    ) {
        try {
            recommendationService.register(dto, id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    
}
