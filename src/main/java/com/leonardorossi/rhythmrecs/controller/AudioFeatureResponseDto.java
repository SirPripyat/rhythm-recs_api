package com.leonardorossi.rhythmrecs.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.AudioFeaturesDto;
import lombok.Data;

import java.util.List;

@Data
public class AudioFeatureResponseDto {
    
    @JsonProperty("audio_features")
    private List<AudioFeaturesDto> audioFeatures;
    
}
