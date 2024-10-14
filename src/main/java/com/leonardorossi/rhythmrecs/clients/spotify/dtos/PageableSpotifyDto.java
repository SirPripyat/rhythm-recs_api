package com.leonardorossi.rhythmrecs.clients.spotify.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageableSpotifyDto<T> {
    
    private String href;
    private List<T> items;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;

}
