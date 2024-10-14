package com.leonardorossi.rhythmrecs.services;

import com.leonardorossi.rhythmrecs.domain.FavoritesGenres;
import com.leonardorossi.rhythmrecs.domain.Person;
import com.leonardorossi.rhythmrecs.repositories.FavoritesGenresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FavoritesGenresService {
    
    private final FavoritesGenresRepository favoritesGenresRepository;
    
    public void register(List<String> genres, Long personId) {
        Person person = new Person();
        Person buildedPerson = person.buildPerson(personId);
        
        List<FavoritesGenres> favoritesGenres = genres.stream()
            .map(genre -> FavoritesGenres.builder()
                    .genre(genre)
                    .person(buildedPerson)
                    .build())
            .collect(Collectors.toList());
        
        favoritesGenresRepository.saveAll(favoritesGenres);
    }
    
}
