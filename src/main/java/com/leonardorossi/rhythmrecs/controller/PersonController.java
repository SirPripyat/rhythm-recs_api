package com.leonardorossi.rhythmrecs.controller;

import com.leonardorossi.rhythmrecs.dtos.PersonDto;
import com.leonardorossi.rhythmrecs.services.PersonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
@Log4j2
@Transactional
public class PersonController {
    
    private final PersonService personService;
    
    @PostMapping()
    public ResponseEntity<Long> register(@RequestBody PersonDto dto) throws BadRequestException {
        try {
            return ResponseEntity.ok(personService.create(dto));
        } catch (Exception e) {
            log.error(e);
            throw new BadRequestException(e.getMessage());
        }
    }
}
