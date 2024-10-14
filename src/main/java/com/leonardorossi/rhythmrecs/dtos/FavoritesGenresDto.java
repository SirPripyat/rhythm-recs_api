package com.leonardorossi.rhythmrecs.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record FavoritesGenresDto(
    List<String> genres,
    Long personId
) {
}
