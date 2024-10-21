package com.leonardorossi.rhythmrecs.services;

import com.leonardorossi.rhythmrecs.clients.spotify.SpotifyClient;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.AudioFeaturesDto;
import com.leonardorossi.rhythmrecs.clients.spotify.dtos.TrackDto;
import com.leonardorossi.rhythmrecs.controller.AudioFeatureResponseDto;
import com.leonardorossi.rhythmrecs.domain.AudioFeature;
import com.leonardorossi.rhythmrecs.domain.Person;
import com.leonardorossi.rhythmrecs.domain.RecommendationRating;
import com.leonardorossi.rhythmrecs.dtos.RegisterRecommendationDto;
import com.leonardorossi.rhythmrecs.repositories.AudioFeatureRepository;
import com.leonardorossi.rhythmrecs.repositories.PersonRepository;
import com.leonardorossi.rhythmrecs.repositories.RecommendationRatingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class RecommendationService {
    
    private static final int LIMIT_TRACKS_BY_GENRE = 5;
    private static final double SIMILARITY_THRESHOLD = 0.1;
    
    private final RecommendationRatingRepository recommendationRatingRepository;
    private final PersonRepository personRepository;
    private final AudioFeatureRepository audioFeatureRepository;
    
    private final SpotifyClient spotifyClient;
    private final SpotifyAuthService spotifyAuthService;
    
    @Qualifier("applicationTaskExecutor")
    private final TaskExecutor taskExecutor;
    
    public List<TrackDto> execute(long personId) throws ExecutionException, InterruptedException {
        String token = spotifyAuthService.getToken();
        
        String tracksIds = String.join(",", personRepository.findTrackSpotifyIdByPersonId(personId));
        
        List<AudioFeaturesDto> userFeatures = recoverAudioFeatures(tracksIds, token);
        AudioFeaturesDto average = calculateAverageAudioFeatures(userFeatures);
        registerAudioFeature(average, true, personId);
        
        List<TrackDto> potentialTracks = getTracksByUserPreferences(personId, token);
        List<String> potentialTrackIds = potentialTracks.stream()
            .map(TrackDto::getId)
            .collect(Collectors.toList());
        
        List<AudioFeaturesDto> potentialFeatures = recoverAudioFeatures(String.join(",", potentialTrackIds), token);
        
        Map<String, TrackDto> potentialTrackMap = potentialTracks.stream()
            .collect(Collectors.toMap(TrackDto::getId, Function.identity()));
        
        return potentialFeatures.stream()
            .filter(potential -> isSimilar(average, potential))
            .map(potential -> {
                registerAudioFeature(potential, false, personId);
                return potentialTrackMap.get(potential.getId());
            })
            .limit(10)
            .collect(Collectors.toList());
    }
    
    private void registerAudioFeature(AudioFeaturesDto dto, boolean isAverage, long personId) {
        AudioFeature entity = AudioFeature.builder()
            .trackId(dto.getId())
            .acousticness(dto.getAcousticness())
            .danceability(dto.getDanceability())
            .durationMs(dto.getDurationMs())
            .instrumentalness(dto.getInstrumentalness())
            .liveness(dto.getLiveness())
            .loudness(dto.getLoudness())
            .tempo(dto.getTempo())
            .isAverage(isAverage)
            .person(Person.buildPerson(personId))
            .build();
        
        audioFeatureRepository.save(entity);
    }
    
    private List<AudioFeaturesDto> recoverAudioFeatures(String tracksIds, String token) {
        AudioFeatureResponseDto response = spotifyClient.getAudioFeatures(tracksIds, token);
        
        return response.getAudioFeatures();
    }
    
    private AudioFeaturesDto calculateAverageAudioFeatures(List<AudioFeaturesDto> audioFeaturesList) {
        if (audioFeaturesList == null || audioFeaturesList.isEmpty()) {
            return new AudioFeaturesDto();
        }
        
        AudioFeaturesDto average = new AudioFeaturesDto();
        
        average.setAcousticness(calculateAverage(audioFeaturesList, AudioFeaturesDto::getAcousticness));
        average.setDanceability(calculateAverage(audioFeaturesList, AudioFeaturesDto::getDanceability));
        average.setDurationMs(calculateAverage(audioFeaturesList, AudioFeaturesDto::getDurationMs));
        average.setInstrumentalness(calculateAverage(audioFeaturesList, AudioFeaturesDto::getInstrumentalness));
        average.setLiveness(calculateAverage(audioFeaturesList, AudioFeaturesDto::getLiveness));
        average.setLoudness(calculateAverage(audioFeaturesList, AudioFeaturesDto::getLoudness));
        average.setTempo(calculateAverage(audioFeaturesList, AudioFeaturesDto::getTempo));
        
        return average;
    }
    
    private List<TrackDto> getTracksByUserPreferences(long personId, String token) {
        List<String> genresList = personRepository.findGenresByPersonId(personId);
        
        List<CompletableFuture<List<TrackDto>>> futures = genresList.stream()
            .map(genre -> CompletableFuture.supplyAsync(() -> {
                String query = "genre:" + genre;
                return spotifyClient
                    .searchTracks(query, "track", LIMIT_TRACKS_BY_GENRE, token)
                    .getTracks()
                    .getItems();
            }, taskExecutor).exceptionally(ex -> {
                log.error(ex.getMessage(), ex);
                
                throw new IllegalArgumentException(ex);
            }))
            .toList();
        
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        
        CompletableFuture<List<TrackDto>> allTracksFuture = allFutures.thenApply(v ->
            futures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
        );
        
        try {
            return allTracksFuture.get();
        } catch (InterruptedException | ExecutionException e) {
             log.error("Error fetching potential tracks", e);
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }
    
    private double calculateAverage(List<AudioFeaturesDto> list, ToDoubleFunction<AudioFeaturesDto> mapper) {
        return list.stream()
            .mapToDouble(mapper)
            .average()
            .orElse(0);
    }
    
    private boolean isSimilar(AudioFeaturesDto userFeatures, AudioFeaturesDto trackFeatures) {
        int similarCount = 0;
        
        if (Math.abs(userFeatures.getAcousticness() - trackFeatures.getAcousticness()) < SIMILARITY_THRESHOLD) {
            similarCount++;
        }
        if (Math.abs(userFeatures.getDanceability() - trackFeatures.getDanceability()) < SIMILARITY_THRESHOLD) {
            similarCount++;
        }
        if (Math.abs(userFeatures.getInstrumentalness() - trackFeatures.getInstrumentalness()) < SIMILARITY_THRESHOLD) {
            similarCount++;
        }
        if (Math.abs(userFeatures.getLiveness() - trackFeatures.getLiveness()) < SIMILARITY_THRESHOLD) {
            similarCount++;
        }
        if (Math.abs(userFeatures.getLoudness() - trackFeatures.getLoudness()) < SIMILARITY_THRESHOLD) {
            similarCount++;
        }
        if (Math.abs(userFeatures.getTempo() - trackFeatures.getTempo()) < SIMILARITY_THRESHOLD) {
            similarCount++;
        }
        
        return similarCount >= 3;
    }
    
    public void register(List<RegisterRecommendationDto> dtoList, long personId) {
        List<RecommendationRating> recommendationRating = dtoList.stream()
            .map(dto -> RecommendationRating.builder()
                .rating(dto.rating().toString())
                .trackId(dto.id())
                .person(Person.buildPerson(personId))
                .build())
            .collect(Collectors.toList());
        
        recommendationRatingRepository.saveAll(recommendationRating);
    }
    
}
