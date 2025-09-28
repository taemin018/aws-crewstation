package com.example.crewstation.member.mapperTests;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.mapper.member.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MemberMapperTests {
    @Autowired
    MemberMapper memberMapper;

    @Test
    public void joinTest(){
        MemberVO memberVO = MemberVO.builder()
                .memberName("test")
                .memberPhone("01012341234")
                .memberEmail("test@gmail.com")
                .memberBirth("20000101")
                .memberGender(Gender.FEMALE)
                .memberPassword("1234")
                .memberProvider(MemberProvider.CREWSTATION)
                .memberRole(MemberRole.MEMBER)
                .build();

        memberMapper.insert(memberVO);
    }

    @Test
    public void emailTest(){
        boolean check =  memberMapper.selectEmail("test@gmail.com");

        log.info(String.valueOf(check));
    }
}
