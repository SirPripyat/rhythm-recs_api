package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    
    @FormProperty("grant_type")
    private String grantType;
    
    @FormProperty("client_id")
    private String clientId;
    
    @FormProperty("client_secret")
    private String clientSecret;

    public static LoginRequestDto buildObject() {
        return LoginRequestDto.builder()
            .grantType("client_credentials")
            .clientId("97c47754b33b42c09dc52d5a0ec40caa")
            .clientSecret("a2e554eb78544482bfb47c215d841159")
            .build();
    }
    
}