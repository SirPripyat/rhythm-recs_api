package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchMusicsByArtistsDto {
    
    private List<TrackDto> tracks;
    
}
