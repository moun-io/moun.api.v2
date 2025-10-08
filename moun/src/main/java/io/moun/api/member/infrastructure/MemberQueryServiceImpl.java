package io.moun.api.member.infrastructure;

import io.moun.api.member.domain.Member;
import io.moun.api.member.domain.repository.MemberRepository;
import io.moun.api.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
    }

    @Override
    public List<Member> findAllWithPositions() {
        return memberRepository.findAllWithPositions();
    }

    public Member findWithPositionsById(Long id) {
        Member member = memberRepository.findMemberWithPositionById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
        return member;
    }

}
