package com.leonardorossi.rhythmrecs.dtos;

import lombok.Builder;

@Builder
public record PersonDto(
    String name,
    int age,
    String email
) {
}
