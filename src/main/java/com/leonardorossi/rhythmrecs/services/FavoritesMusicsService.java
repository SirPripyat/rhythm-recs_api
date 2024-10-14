package com.leonardorossi.rhythmrecs.services;

import com.leonardorossi.rhythmrecs.clients.spotify.SpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.*;
import com.leonardorossi.rhythmrecs.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritesMusicsService {
    
    private final PersonRepository personRepository;
    private final SpotifyClient spotifyClient;
    
    public List<TrackDto> getMusicsByFavoritesArtistsFromPerson(
        long personId,
        String token
    ) {
        List<String> spotifyIdsList =
            personRepository.findSpotifyIdByPersonId(personId);
        
        List<TrackDto> trackList = new ArrayList<>();
        
        spotifyIdsList.forEach(id -> {
            SearchMusicsByArtistsDto response = spotifyClient
                .searchMusicsByGenre(
                    id,
                    "BR",
                    token
                );
            
            List<TrackDto> tracks = response.getTracks();
            
            if (tracks.size() > 5) {
                tracks = tracks.subList(0, 5);
            }
            
            trackList.addAll(tracks);
        });
        
        return trackList;
    }
    
    public List<TrackDto> search(String query, String token) {
        SearchTracksByQueryDto dto = spotifyClient.searchTracks(
            query,
            "track",
            20,
            token
        );
        
        return dto.getTracks().getItems();
    }
}
