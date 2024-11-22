package io.moun.api.song.controller.dto;

import io.moun.api.song.domain.GenreType;
import io.moun.api.song.domain.VibeType;
import lombok.Data;

import java.util.Set;

@Data
public class SongRequest {
    
    private String title;
    private String description;
    private Set<GenreType> songGenres;
    private Set<VibeType> songVibes;
}
