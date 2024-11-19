package io.moun.api.member.infrastructure;

import io.moun.api.member.domain.Member;
import io.moun.api.member.domain.repository.MemberRepository;
import io.moun.api.member.service.MemberCommandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

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

    public Member update(Member member) {
        Member existingMember = memberRepository.findById(member.getId()).orElseThrow(() -> new RuntimeException("Member not found"));
        existingMember.setDescription(member.getDescription());
        existingMember.setProfilePictureUrl(member.getProfilePictureUrl());
        existingMember.setDisplayName(member.getDisplayName());
        existingMember.setPositions(member.getPositions());
        existingMember.setSns(member.getSns());
        return memberRepository.save(existingMember);
    }
}
