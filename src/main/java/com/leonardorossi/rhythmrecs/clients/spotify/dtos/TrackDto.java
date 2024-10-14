package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrackDto {
    
    @JsonProperty("duration_ms")
    private long durationMs;
    
    @JsonProperty("album")
    private AlbumDto album;
    
    @JsonProperty("external_urls")
    private ExternalUrlsResponseDto externalUrls;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
}
