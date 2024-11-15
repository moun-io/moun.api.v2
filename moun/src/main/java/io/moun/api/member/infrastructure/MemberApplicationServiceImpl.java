package io.moun.api.member.infrastructure;

import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Member;
import io.moun.api.member.service.MemberApplicationService;
import io.moun.api.member.service.MemberService;
import io.moun.api.security.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;


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


    @Override
    public void deleteMemberAuth(String username) {


    }
}

