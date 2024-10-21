package com.leonardorossi.rhythmrecs.dtos;

import com.leonardorossi.rhythmrecs.enums.RatingEnum;
import lombok.Builder;

public record RegisterRecommendationDto(
    String id,
    RatingEnum rating
) {
}
