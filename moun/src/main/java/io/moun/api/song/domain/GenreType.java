package io.moun.api.song.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenreType {
    IDOL,
    POP,
    INDIE,
    HIPHOP,
    BALLAD,
    UK_GARAGE
}
