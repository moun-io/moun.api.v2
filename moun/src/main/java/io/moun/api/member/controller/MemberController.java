package io.moun.api.member.controller;

import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Member;
//import io.moun.api.member.service.MemberAuthService;
import io.moun.api.member.service.MemberService;
import jakarta.validation.Valid;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<String> getMembers(){
        return ResponseEntity.ok("Members");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable @Valid Long id) {
        Member member = memberService.findById(id);
        return ResponseEntity.ok(member);
    }


//    @PostMapping
//    public ResponseEntity<String> createMember(@Valid @RequestBody RegisterRequest registerRequest) {
//
//    }
}


