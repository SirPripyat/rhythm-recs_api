package com.leonardorossi.rhythmrecs.repositories;

import com.leonardorossi.rhythmrecs.domain.FavoritesTracks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesTracksRepository extends JpaRepository<FavoritesTracks, Long> {
}
