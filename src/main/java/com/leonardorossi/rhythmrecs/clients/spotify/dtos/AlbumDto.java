package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AlbumDto {
    
    @JsonProperty("images")
    private List<ImageResponseDto> images;
    
    @JsonProperty("artists")
    private List<ArtistResponseDto> artists;
    
}
