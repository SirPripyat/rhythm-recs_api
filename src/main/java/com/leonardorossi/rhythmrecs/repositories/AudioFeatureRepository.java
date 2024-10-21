package com.leonardorossi.rhythmrecs.repositories;

import com.leonardorossi.rhythmrecs.domain.AudioFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioFeatureRepository extends JpaRepository<AudioFeature, Long> {
}
