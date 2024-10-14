package com.leonardorossi.rhythmrecs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@Table(name = "favorites_artists")
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesArtists {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "spotify_id")
    private String spotifyId;
    
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    
}
