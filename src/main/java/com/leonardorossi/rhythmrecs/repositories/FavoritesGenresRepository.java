package com.leonardorossi.rhythmrecs.repositories;

import com.leonardorossi.rhythmrecs.domain.FavoritesGenres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesGenresRepository extends JpaRepository<FavoritesGenres, Long> {
}
