package com.leonardorossi.rhythmrecs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import com.leonardorossi.rhythmrecs.enums.RatingEnum;

import java.util.List;

@Entity
@SuperBuilder
@Data
@Table(name = "recommendation_rating")
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "track_id")
    private String trackId;

    @Column(name = "rating")
    private String rating;
    
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    
}
