package io.moun.api.member.controller;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.controller.dto.MemberUpdateRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.service.MemberApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberApplicationService memberApplicationService;

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody RegisterRequest registerRequest) {
        MemberResponse memberResponse = memberApplicationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberUpdateRequest> updateMember(@RequestBody MemberUpdateRequest memberUpdateRequest, @PathVariable Long id) {
        memberApplicationService.update(memberUpdateRequest, id);
        return ResponseEntity.status(HttpStatus.OK).body(memberUpdateRequest);
    }

    @GetMapping
    public ResponseEntity<String> getMembers() {
        return ResponseEntity.ok("Members");
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        MemberResponse memberResponse = memberApplicationService.findWithPositionsById(id);// 로그 추가
        return ResponseEntity.ok(memberResponse);
    }

//    @GetMapping("/me")
//    public ResponseEntity<MemberResponse> getMember(Principal principal) {
//        MemberResponse memberResponse = memberService.findByUsername(principal.getName());
//        return ResponseEntity.ok(memberResponse);
//    }

//    @GetMapping
//    public ResponseEntity<MemberResponse> getMembers(@RequestParam(required = false) String name) {
//
//    }


}


