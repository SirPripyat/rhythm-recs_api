package com.leonardorossi.rhythmrecs.mappers;

import com.leonardorossi.rhythmrecs.domain.Person;
import com.leonardorossi.rhythmrecs.dtos.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    
    public Person toEntity(PersonDto dto) {
        return Person.builder()
            .name(dto.name())
            .age(dto.age())
            .email(dto.email())
            .build();
    }
    
}
