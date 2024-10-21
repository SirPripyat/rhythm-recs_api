package com.leonardorossi.rhythmrecs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Data
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "age")
    private int age;
    
    @Column(name = "email")
    private String email;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<FavoritesGenres> genresList;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<FavoritesArtists> artistsList;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<AudioFeature> audioFeatures;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<RecommendationRating> recommendationRatings;
    
    public static Person buildPerson(Long id) {
        return Person.builder()
            .id(id)
            .build();
    }
    
}
