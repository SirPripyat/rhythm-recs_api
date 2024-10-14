package com.leonardorossi.rhythmrecs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RhythmRecsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(RhythmRecsApplication.class, args);
    }
    
}
