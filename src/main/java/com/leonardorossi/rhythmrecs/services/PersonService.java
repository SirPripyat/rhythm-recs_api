package com.leonardorossi.rhythmrecs.services;

import com.leonardorossi.rhythmrecs.domain.Person;
import com.leonardorossi.rhythmrecs.dtos.PersonDto;
import com.leonardorossi.rhythmrecs.mappers.PersonMapper;
import com.leonardorossi.rhythmrecs.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    
    private final PersonRepository repository;
    private final PersonMapper mapper;
    
    public Long create(PersonDto dto) {
        Person person = mapper.toEntity(dto);
        
        return repository.save(person).getId();
    }
}
