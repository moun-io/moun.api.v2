package io.moun.api.member.infrastructure;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.domain.Member;
import io.moun.api.member.domain.repository.MemberRepository;
import io.moun.api.member.service.MemberService;
import io.moun.api.security.domain.Auth;
import io.moun.api.security.infrastructure.JwtTokenHelper;
import io.moun.api.security.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
//    private final JwtTokenHelper jwtTokenHelper;

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

//    @Override
//    public MemberResponse findByUsername(String username) {
//        Member member = memberRepository.findByUsername(username).orElse(null);
//        return modelMapper.map(member, MemberResponse.class);
//    }

    @Transactional
    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member saveDefault() {
        Member member = new Member();
        member.setDescription("please introduce yourself");
        member.setProfilePictureUrl("");
        member.setDisplayName("Mounie");
        return memberRepository.save(member);
    }



}
