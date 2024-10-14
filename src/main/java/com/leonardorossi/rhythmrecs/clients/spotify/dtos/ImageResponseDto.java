package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {
    
    private String height;
    
    @JsonProperty("url")
    private String url;
    
    private String width;
    
}
