package io.moun.api.member.domain;

import io.moun.api.common.domain.BaseEntity;
import io.moun.api.position.Position;
import io.moun.api.security.domain.Auth;
import io.moun.api.song.domain.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

//    @OneToOne(fetch = FetchType.LAZY) // 실제로 데이터 로딩하지 않음
//    @JoinColumn(name = "auth_username", referencedColumnName = "username") // 외래 키 설정
//    private Auth auth; // 실제로 User 객체를 로딩하지 않음

    @Embedded
    private SNS sns;
    
    @NotNull
    private String displayName;
    @NotNull
    private String description;
    @NotNull
    private boolean verified;

    private String profilePictureUrl;

    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "member")
    private List<Song> songs = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy="member", cascade = CascadeType.ALL)
    private List<Position> positions = new ArrayList<>();
}
