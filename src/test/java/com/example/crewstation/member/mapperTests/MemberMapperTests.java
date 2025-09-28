package com.example.crewstation.member.mapperTests;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.mapper.member.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class MemberMapperTests {
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void joinTest() {
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
    public void emailTest() {
        boolean check = memberMapper.selectEmail("test@gmail.com");

        log.info(String.valueOf(check));
    }

    @Test
    public void testInsertGuest() {
        MemberDTO memberDTO = new MemberDTO();
        memberMapper.insertGuest(memberDTO);
    }

    public void loginTest() {
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setMemberEmail("test@gmail.com");
        String password = passwordEncoder.encode("1234qwer");
        memberDTO.setMemberPassword(password);
        log.info(password);

        memberMapper.selectForLogin(memberDTO);
        log.info(String.valueOf(memberMapper.selectForLogin(memberDTO)));
    }
}
