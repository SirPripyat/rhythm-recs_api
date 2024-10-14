package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GenreResponseDto {
    private List<String> genres;
}
