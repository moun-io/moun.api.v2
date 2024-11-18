package io.moun.api.member.infrastructure;

import io.moun.api.member.domain.Member;
import io.moun.api.member.domain.Position;
import io.moun.api.member.domain.repository.MemberRepository;
import io.moun.api.member.domain.repository.PositionRepository;
import io.moun.api.member.service.MemberCommandService;
import io.moun.api.security.infrastructure.JwtTokenHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final JwtTokenHelper jwtTokenHelper;
    private final PositionRepository positionRepository;
    private final EntityManager entityManager;

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }
    @Transactional
    public Member saveDefault() {
        Member member = new Member();
        member.setDescription("please introduce yourself");
        member.setProfilePictureUrl("");
        member.setDisplayName("Mounie");
        member.setVerified(false);
        return memberRepository.save(member);
    }

    @Transactional
    public Member update(Member member,Long id) {
        if (!jwtTokenHelper.getMemberId().equals(id))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Member existingMember = memberRepository.findMemberWithPositionById(id).orElseThrow(()-> new EntityNotFoundException("MEMBER NOT FOUND"));
        existingMember.setDescription(member.getDescription());
        existingMember.setProfilePictureUrl(member.getProfilePictureUrl());
        existingMember.setDisplayName(member.getDisplayName());
        existingMember.setSns(member.getSns());
        memberRepository.save(existingMember);
        positionRepository.deleteAllByMemberId(id);  // Now delete the positions
        for(Position newPosition : member.getPositions()){
            newPosition.setMember(existingMember);

            positionRepository.save(newPosition);
        }
        return existingMember;
    }
}
