package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record LoginResponseDto(
    String accessToken
) {
}