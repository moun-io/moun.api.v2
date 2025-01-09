package io.moun.api.member.domain;

import io.moun.api.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class memberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;

    Member member1;
    @BeforeEach
    void setUp() {
        PositionType[] positionTypes = PositionType.values();
        List<Position> positions = Arrays.stream(positionTypes).map((positionType)->{
            return new Position(positionType, null);
        }).toList();
        memberRepository.deleteAll();
        member1 = Member.builder().id(1L).sns(SNS.builder().instagramSNS("instagram1").soundCloudSNS("soundcloud1").build())
                .displayName("username1")
                .description("description1")
                .positions(positions.subList(0,1))
                .verified(false)
                .profilePictureUrl(null)
                .build();
    }
    @Test
    void test(){
        memberRepository.save(member1);
    }
}
