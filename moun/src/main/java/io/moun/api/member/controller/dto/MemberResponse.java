package io.moun.api.member.controller.dto;

import io.moun.api.member.domain.PositionType;
import io.moun.api.member.domain.SNS;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(fetch = FetchType.LAZY) // 실제로 데이터 로딩하지 않음
//    @JoinColumn(name = "auth_id", referencedColumnName = "id") // 외래 키 설정
//    private Auth auth; // 실제로 User 객체를 로딩하지 않음

    @Embedded
    private SNS sns;

    @NotNull
    private String displayName;
    @NotNull
    private String description;

    private String profilePictureUrl;
    private List<PositionType> positionType = new ArrayList<>();
}
