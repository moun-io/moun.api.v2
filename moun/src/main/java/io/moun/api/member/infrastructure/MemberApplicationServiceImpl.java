package io.moun.api.member.infrastructure;

import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Member;
import io.moun.api.member.service.MemberApplicationService;
import io.moun.api.member.service.MemberService;
import io.moun.api.security.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
public class MemberApplicationServiceImpl implements MemberApplicationService {
    private MemberService memberService;
    private AuthService authService;
    @Autowired
    public MemberApplicationServiceImpl(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }


    @Override
    @Transactional
    public Member registerMemberAuth(RegisterRequest registerRequest) {
        Member savedMember =memberService.saveDefault();
        authService.save(registerRequest,savedMember);
        return savedMember;
    }



    @Override
    public void deleteMemberAuth(String username) {


    }
}

