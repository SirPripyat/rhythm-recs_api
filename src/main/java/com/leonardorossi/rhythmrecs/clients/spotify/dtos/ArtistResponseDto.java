package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistResponseDto {
    
    @JsonProperty("external_urls")
    private ExternalUrlsResponseDto externalUrls;
    
    private List<ImageResponseDto> images;
    
    private List<String> genres;
    
    private String id;
    
    private String name;
    
}
