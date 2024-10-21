package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AudioFeaturesDto {
    
    @JsonProperty("acousticness")
    private Double acousticness;
    
    @JsonProperty("danceability")
    private Double danceability;
    
    @JsonProperty("duration_ms")
    private Double durationMs;
    
    @JsonProperty("instrumentalness")
    private Double instrumentalness;
    
    @JsonProperty("liveness")
    private Double liveness;
    
    @JsonProperty("loudness")
    private Double loudness;
 
    @JsonProperty("tempo")
    private Double tempo;
    
    @JsonProperty("id")
    private String id;
    
}
