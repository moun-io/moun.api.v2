package io.moun.api.member.domain;

import io.moun.api.common.domain.BaseEntity;
import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.song.domain.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private List<Position> positions = new ArrayList<>();


    public void addPosition (Position position){
        positions.add(new Position());
    }
    public void addPosition (PositionType positionType){
        positions.add(new Position(positionType,this));
    }


}
