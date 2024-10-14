package com.leonardorossi.rhythmrecs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@Table(name = "favorites_genres")
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesGenres {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "genre")
    private String genre;
    
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    
}
