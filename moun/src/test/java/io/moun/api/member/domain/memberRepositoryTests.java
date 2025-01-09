package io.moun.api.member.domain;

import io.moun.api.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class memberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;
    @Test
    void test(){
        memberRepository.deleteAll();
    }
}
