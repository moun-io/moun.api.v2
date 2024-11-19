//package io.moun.api.member.infrastructure;
//
//import io.moun.api.member.controller.dto.RegisterRequest;
//import io.moun.api.member.domain.Member;
//import io.moun.api.member.service.MemberAuthService;
//import io.moun.api.member.service.MemberService;
//import io.moun.api.security.service.AuthService;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//public class MemberAuthServiceImpl implements MemberAuthService {
//    private MemberService memberService;
//    private AuthService authService;
//    @Autowired
//    public MemberAuthServiceImpl(MemberService memberService, AuthService authService) {
//        this.memberService = memberService;
//        this.authService = authService;
//    }
//
//
//    @Override
//    @Transactional
//    public void register(RegisterRequest registerRequest) {
//        authService.registerAuth(registerRequest);
//        memberService.save(new Member());
//    }
//
//    @Override
//    public void deleteUser(String username) {
//
//
//    }
//}
//
