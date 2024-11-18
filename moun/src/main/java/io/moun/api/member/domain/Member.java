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

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Position> positions = new ArrayList<>();


    public MemberResponse toMemberResponse(ModelMapper modelMapper) {

        // 기본 필드 매핑
        MemberResponse response = modelMapper.map(this, MemberResponse.class);

        // positions 리스트에서 PositionType만 추출하여 반환
        List<PositionType> positionTypes = new ArrayList<>();
        for (Position position : this.positions) {
            positionTypes.add(position.getPositionType());
        }

        // positionType 필드 설정
        response.setPositionType(positionTypes);

        return response;
    }


    //    @OneToOne(fetch = FetchType.LAZY) // 실제로 데이터 로딩하지 않음
//    @JoinColumn(name = "auth_username", referencedColumnName = "username") // 외래 키 설정
//    private Auth auth; // 실제로 User 객체를 로딩하지 않음


//    @ElementCollection
//    @CollectionTable(name = "member_position", joinColumns = @JoinColumn(name = "member_id"))
//    private List<Position> positions = new ArrayList<>();
//    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "member")
//    private List<Song> songs = new ArrayList<>();
//
}
