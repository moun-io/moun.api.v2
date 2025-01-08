package io.moun.api.song.controller.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SongRequest {
    
    private String title;
    private String description;
    private Set<String> songGenres;
    private Set<String> songVibes;
}
