package com.leonardorossi.rhythmrecs.dtos;

import com.leonardorossi.rhythmrecs.clients.spotify.dtos.ArtistResponseDto;

import java.util.List;

public record FavoritesTracksDto(
    String spotifyId,
    String name
) {
}
