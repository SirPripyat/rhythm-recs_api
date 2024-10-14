package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchArtistsByGenreDto {
    
    private PageableSpotifyDto<ArtistResponseDto> artists;
    
}
