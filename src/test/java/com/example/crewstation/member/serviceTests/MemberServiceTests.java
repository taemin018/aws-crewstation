package com.example.crewstation.member.serviceTests;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.mapper.member.MemberMapper;
import com.example.crewstation.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@Slf4j
public class MemberServiceTests {
    @Autowired
    MemberService memberService;

    @Test
    public void joinTest(){
        MemberVO memberVO = MemberVO.builder()
                .memberName("test2")
                .memberPhone("01012341234")
                .memberEmail("test2@gmail.com")
                .memberBirth("20000101")
                .memberGender(Gender.FEMALE)
                .memberPassword("1234")
                .memberProvider(MemberProvider.CREWSTATION)
                .memberRole(MemberRole.MEMBER)
                .build();

        MultipartFile multipartFile = new MultipartFile;

        memberService.join(toVO(), multipartFile);
    }

    @Test
    public void emailTest(){
        boolean check =  memberMapper.selectEmail("test@gmail.com");

        log.info(String.valueOf(check));
    }
}
