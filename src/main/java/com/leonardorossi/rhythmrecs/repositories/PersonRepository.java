package com.leonardorossi.rhythmrecs.repositories;

import com.leonardorossi.rhythmrecs.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
    @Query("select fg.genre from FavoritesGenres fg where fg.person.id = " + ":id")
    List<String> findGenresByPersonId(Long id);
    
    @Query("select fa.spotifyId from FavoritesArtists fa where fa.person.id =" + " :id")
    List<String> findSpotifyIdByPersonId(Long id);
    
    @Query("select ft.spotifyId from FavoritesTracks ft where ft.person.id = :id")
    List<String> findTrackSpotifyIdByPersonId(Long id);
    
}
