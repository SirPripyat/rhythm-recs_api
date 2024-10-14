package com.leonardorossi.rhythmrecs.services;

import com.leonardorossi.rhythmrecs.clients.spotify.SpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.ArtistResponseDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.SearchArtistsByGenreDto;
import com.leonardorossi.rhythmrecs.domain.FavoritesArtists;
import com.leonardorossi.rhythmrecs.domain.Person;
import com.leonardorossi.rhythmrecs.dtos.FavoritesArtistsDto;
import com.leonardorossi.rhythmrecs.repositories.FavoritesArtistsRepository;
import com.leonardorossi.rhythmrecs.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistsService {
    
    private final FavoritesArtistsRepository repository;
    
    private final PersonRepository personRepository;
    private final SpotifyClient spotifyClient;
    
    public List<ArtistResponseDto> getGenresByPerson(Long personId, String token) {
        List<String> genreList = personRepository.findGenresByPersonId(personId);
        
        List<ArtistResponseDto> artistList = new ArrayList<>();
        
        genreList.forEach(genre -> {
            SearchArtistsByGenreDto response = spotifyClient.searchArtistsByGenre(token, "genre:" + genre, "artist", 5);
            
            artistList.addAll(response.getArtists().getItems());
        });
        
        return artistList;
    }
    
    public List<ArtistResponseDto> searchArtists(String query, String token) {
        SearchArtistsByGenreDto dto = spotifyClient.searchArtist(token, query,
            "artist");
        
        return dto.getArtists().getItems();
    }
    
    public void register(List<FavoritesArtistsDto> dto, long personId) {
        Person person = new Person();
        Person buildedPerson = person.buildPerson(personId);
        
        List<FavoritesArtists> favoritesArtists = dto.stream()
            .map(artist -> FavoritesArtists.builder()
                    .name(artist.name())
                    .spotifyId(artist.spotifyId())
                    .person(buildedPerson)
                    .build())
            .collect(Collectors.toList());
        
        repository.saveAll(favoritesArtists);
    }
}
