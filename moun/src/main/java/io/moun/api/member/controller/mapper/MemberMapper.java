package io.moun.api.member.controller.mapper;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.controller.dto.MemberUpdateRequest;
import io.moun.api.member.domain.Member;
import io.moun.api.member.domain.Position;
import io.moun.api.member.domain.PositionType;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberMapper {
    private final ModelMapper modelMapper;

    public MemberResponse toMemberResponse(Member member) {
        MemberResponse memberResponse = modelMapper.map(member, MemberResponse.class);
        // positions 리스트에서 PositionType만 추출하여 반환
        List<PositionType> positionTypes = new ArrayList<>();
        for (Position position : member.getPositions()) {
            positionTypes.add(position.getPositionType());
        }
        // positionType 필드 설정
        memberResponse.setPositionType(positionTypes);
        return memberResponse;
    }

    public Member toMember(MemberUpdateRequest memberUpdateRequest) {
        Member member = modelMapper.map(memberUpdateRequest, Member.class);
        List<PositionType> positionTypes = new ArrayList<>();
        for (PositionType positionType : memberUpdateRequest.getPositionTypes()) {
            member.addPosition(new Position(positionType, member));
        }
        return member;
    }


}
