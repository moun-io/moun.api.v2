package io.moun.api.member.infrastructure.facade;

import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Member;
import io.moun.api.member.service.MemberApplicationService;
import io.moun.api.member.service.MemberService;
import io.moun.api.security.domain.Auth;
import io.moun.api.security.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberApplicationServiceImpl implements MemberApplicationService {
    private final MemberService memberService;
    private final AuthService authService;



    @Override
    @Transactional
    public Member registerMemberAuth(RegisterRequest registerRequest) {
        Member savedMember = memberService.saveDefault();
        authService.save(registerRequest, savedMember);
        return savedMember;
    }

    public Member findByUsername(String username) {
        Auth auth = authService.findAuthByUsername(username);
        return auth.getMember();
    }


    @Override
    public void deleteMemberAuth(String username) {


    }
}

