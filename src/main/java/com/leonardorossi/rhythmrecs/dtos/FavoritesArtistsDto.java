package com.leonardorossi.rhythmrecs.dtos;

import lombok.Builder;

@Builder
public record FavoritesArtistsDto(
    String spotifyId,
    String name
) {
}
