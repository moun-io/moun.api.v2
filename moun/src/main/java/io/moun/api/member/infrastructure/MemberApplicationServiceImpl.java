package io.moun.api.member.infrastructure;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.controller.dto.MemberUpdateRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.controller.mapper.MemberMapper;
import io.moun.api.member.domain.Member;
import io.moun.api.member.service.MemberApplicationService;
import io.moun.api.member.service.MemberCommandService;
import io.moun.api.member.service.MemberQueryService;
import io.moun.api.security.domain.Auth;
import io.moun.api.security.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class MemberApplicationServiceImpl implements MemberApplicationService {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final AuthService authService;
    private final MemberMapper memberMapper;


    public Member findByUsername(String username) {
        Auth auth = authService.findAuthByUsername(username);
        return auth.getMember();
    }

    @Override
    public MemberResponse findById(Long id) {
        return memberMapper.toMemberResponse(memberQueryService.findById(id));
    }

    public MemberResponse findWithPositionsById(Long id) {
        return memberMapper.toMemberResponse(memberQueryService.findWithPositionsById(id));
    }


    @Override
    @Transactional
    public MemberResponse register(RegisterRequest registerRequest) {
        Member savedMember = memberCommandService.saveDefault();
        authService.save(registerRequest, savedMember);
        return memberMapper.toMemberResponse(savedMember);
    }

    public MemberResponse update(MemberUpdateRequest memberUpdateRequest) {
        Member member = memberMapper.toMember(memberUpdateRequest);
        memberCommandService.update(member);
        return memberMapper.toMemberResponse(member);
    }

    @Override
    public void delete(String username) {


    }
}

