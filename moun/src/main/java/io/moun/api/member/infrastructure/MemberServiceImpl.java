package io.moun.api.member.infrastructure;

import io.moun.api.member.domain.Member;
import io.moun.api.member.domain.repository.MemberRepository;
import io.moun.api.member.service.MemberService;
import io.moun.api.security.domain.Auth;
import io.moun.api.security.infrastructure.JwtTokenHelper;
import io.moun.api.security.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private JwtTokenHelper jwtTokenHelper;
    private AuthService authService;
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, JwtTokenHelper jwtTokenHelper,AuthService authService) {
        this.memberRepository = memberRepository;
        this.jwtTokenHelper = jwtTokenHelper;
        this.authService = authService;
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }
    @Override
    public void saveDefault(Principal principal){
        Auth auth = authService.findAuthByUsername(principal.getName());
        Member member= new Member();
        member.setDescription("please introduce yourself");
        member.setAuth(auth);
        member.setProfilePictureUrl("");
        member.setDisplayName("Mounie");
        memberRepository.save(member);
    }





}
