package io.moun.api.member.domain;

import io.moun.api.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.xml.transform.Source;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Transactional
@SpringBootTest
public class memberRepositoryTests {
    @Autowired
    DefaultListableBeanFactory beanFactory;
    @Autowired
    MemberRepository memberRepository;
    List<Member> members = new ArrayList<>();
    Member member1;
    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        members.add(
                Member.builder()
                .sns(SNS.builder()
                        .instagramSNS("instagram1").soundCloudSNS("soundcloud1").build())
                .displayName("username1")
                .description("description1")
                .verified(false)
                .profilePictureUrl(null)
                .build());
        members.add(
                Member.builder()
                        .sns(SNS.builder()
                                .instagramSNS("instagram2").soundCloudSNS("soundcloud2").build())
                        .displayName("username2")
                        .description("description2")
                        .verified(false)
                        .profilePictureUrl(null)
                        .build());
        members.add(
                Member.builder()
                        .sns(SNS.builder()
                                .instagramSNS("instagram3").soundCloudSNS("soundcloud3").build())
                        .displayName("username3")
                        .description("description3")
                        .verified(false)
                        .profilePictureUrl(null)
                        .build());

        members.add(
                Member.builder()
                        .sns(SNS.builder()
                                .instagramSNS("instagram4").soundCloudSNS("soundcloud4").build())
                        .displayName("username4")
                        .description("description4")
                        .verified(false)
                        .profilePictureUrl(null)
                        .build());

        members.add(
                Member.builder()
                        .sns(SNS.builder()
                                .instagramSNS("instagram5").soundCloudSNS("soundcloud5").build())
                        .displayName("username5")
                        .description("description5")
                        .verified(false)
                        .profilePictureUrl(null)
                        .build());

//        PositionType[] positionTypes = PositionType.values();
//        List<Position> positions = Arrays.stream(positionTypes).map((positionType)->{
//            return new Position(positionType, null);
//        }).toList();
    }


    @Test
    void beans(){
        System.out.println("Beans : ");
        for(String name : beanFactory.getBeanDefinitionNames())
        System.out.println(name + " : \t" + beanFactory.getBean(name).getClass().getName());
    }
    @Test
    void saveAndFindTest(){
        for ( Member member : members ) {
            memberRepository.save(member);
        }
        assert memberRepository.count() == members.size();
        Iterator<Member> it= members.iterator();
        while(it.hasNext()){
            Member member  = it.next();
            Member memberDB = memberRepository.findById(member.getId()).orElseThrow();
            assert memberDB.equals(member);
        }
    }
    @Test
    void saveAllandFindAllTest(){
        memberRepository.saveAll(members);
        List<Member> membersDB = memberRepository.findAll();
        assert membersDB.size() == members.size();
        for (Member member : members) {
            assert membersDB.contains(member);
        }
    }

}
