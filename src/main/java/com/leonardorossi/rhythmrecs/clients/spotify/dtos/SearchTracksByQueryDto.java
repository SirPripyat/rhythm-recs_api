package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchTracksByQueryDto {
    
    private PageableSpotifyDto<TrackDto> tracks;
    
}