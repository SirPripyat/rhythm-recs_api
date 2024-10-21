package com.leonardorossi.rhythmrecs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RatingEnum {
    LIKE("LIKE"),
    DISLIKE("DISLIKE"),
    NA("N/A");
    
    private final String value;
    
    @Override
    public String toString() {
        return this.value;
    }
}
