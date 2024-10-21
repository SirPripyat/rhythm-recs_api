package com.leonardorossi.rhythmrecs.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@Table(name = "audio_feature")
@NoArgsConstructor
@AllArgsConstructor
public class AudioFeature {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "track_id")
    private String trackId;
    
    @Column(name = "acousticness")
    private Double acousticness;
    
    @Column(name = "danceability")
    private Double danceability;
    
    @Column(name = "duration_ms")
    private Double durationMs;
    
    @Column(name = "instrumentalness")
    private Double instrumentalness;
    
    @Column(name = "liveness")
    private Double liveness;
    
    @Column(name = "loudness")
    private Double loudness;
    
    @Column(name = "tempo")
    private Double tempo;
    
    @Column(name = "is_average")
    private Boolean isAverage;
    
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    
}
