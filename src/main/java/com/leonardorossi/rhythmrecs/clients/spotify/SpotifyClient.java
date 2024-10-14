package com.leonardorossi.rhythmrecs.clients.spotify;

import com.leonardorossi.rhythmrecs.clients.spotify.dtos.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SpotifyClient", url = "https://api.spotify.com/v1")
public interface SpotifyClient {
    
    @GetMapping(value = "/recommendations/available-genre-seeds")
    GenreResponseDto getAvailableGenreSeeds(@RequestHeader("Authorization") String bearerToken);
    
    @GetMapping("/search")
    SearchArtistsByGenreDto searchArtistsByGenre(
        @RequestHeader("Authorization") String authorization,
        @RequestParam("q") String genreQuery,
        @RequestParam("type") String type,
        @RequestParam("limit") int limit
    );
    
    @GetMapping("/search")
    SearchArtistsByGenreDto searchArtist(
        @RequestHeader("Authorization") String authorization,
        @RequestParam("q") String query,
        @RequestParam("type") String type
    );
    
    @GetMapping("/search")
    SearchTracksByQueryDto searchTracks(
        @RequestParam("q") String query,
        @RequestParam("type") String type,
        @RequestParam("limit") int limit,
        @RequestHeader("Authorization") String authorization
    );
    
    @GetMapping("/artists/{id}/top-tracks")
    SearchMusicsByArtistsDto searchMusicsByGenre(
        @RequestParam("id") String id,
        @RequestParam("market") String market,
        @RequestHeader("Authorization") String authorization
    );
    
}
