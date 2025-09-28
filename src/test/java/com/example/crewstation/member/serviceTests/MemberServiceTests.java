package com.example.crewstation.member.serviceTests;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.AddressDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
@Slf4j
public class MemberServiceTests {
    @Autowired
    MemberService memberService;

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

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressDetail("test address");
        addressDTO.setAddressZipCode("123");
        addressDTO.setAddress("test address");

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberName(memberVO.getMemberName());
        memberDTO.setMemberPhone(memberVO.getMemberPhone());
        memberDTO.setMemberEmail(memberVO.getMemberEmail());
        memberDTO.setMemberBirth(memberVO.getMemberBirth());
        memberDTO.setMemberGender(memberVO.getMemberGender());
        memberDTO.setMemberPassword(memberVO.getMemberPassword());
        memberDTO.setMemberProvider(memberVO.getMemberProvider());
        memberDTO.setMemberRole(memberVO.getMemberRole());
        memberDTO.setAddressDTO(addressDTO);


        // MockMultipartFile 생성 (임의 파일 넣기)
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",                          // 필드 이름
                "test.png",                      // 파일명
                "image/png",                     // MIME 타입
                "dummy image content".getBytes() // 파일 내용
        );

        memberService.join(memberDTO, multipartFile);
    }

    @Test
    public void emailTest(){
        boolean check =  memberService.checkEmail("test@gmail.com");

        log.info(String.valueOf(check));
    }
}
