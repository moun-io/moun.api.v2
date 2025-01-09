package io.moun.api.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.moun.api.common.BaseEntity;
import io.moun.api.security.domain.Auth;
import io.moun.api.song.domain.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private SNS sns;
    @NotNull
    private String displayName;
    @NotNull
    private String description;
    @NotNull
    private boolean verified;
    private String profilePictureUrl;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Position> positions = new ArrayList<>();
    
    public void addPosition(Position position) {
        positions.add(position);
    }
}
