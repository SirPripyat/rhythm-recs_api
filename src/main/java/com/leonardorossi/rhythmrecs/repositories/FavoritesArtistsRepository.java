package com.leonardorossi.rhythmrecs.repositories;

import com.leonardorossi.rhythmrecs.domain.FavoritesArtists;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesArtistsRepository extends JpaRepository<FavoritesArtists, Long> {
}
