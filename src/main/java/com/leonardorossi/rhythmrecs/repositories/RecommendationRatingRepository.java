package com.leonardorossi.rhythmrecs.repositories;

import com.leonardorossi.rhythmrecs.domain.RecommendationRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRatingRepository extends JpaRepository<RecommendationRating, Long> {
}
