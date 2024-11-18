package io.moun.api.member.infrastructure;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.controller.dto.RegisterRequest;
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
    private final ModelMapper modelMapper;

    public Member findByUsername(String username) {
        Auth auth = authService.findAuthByUsername(username);
        return auth.getMember();
    }

    @Override
    public MemberResponse findById(Long id) {
        return memberQueryService.findById(id).toMemberResponse(modelMapper);
    }
    public MemberResponse findWithPositionsById(Long id) {
        return memberQueryService.findWithPositionsById(id).toMemberResponse(modelMapper);
    }


    @Override
    @Transactional
    public MemberResponse register(RegisterRequest registerRequest) {
        Member savedMember = memberCommandService.saveDefault();
        authService.save(registerRequest, savedMember);
        return savedMember.toMemberResponse();
    }




    @Override
    public void delete(String username) {


    }
}

