package com.example.crewstation.service.member;

import com.example.crewstation.domain.member.AddressVO;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.AddressDTO;
import com.example.crewstation.dto.member.MemberDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
//    회원가입
    public void join(MemberDTO memberDTO, MultipartFile multipartFile, AddressDTO addressDTO);

//    이메일 중복 검사
    public boolean checkEmail(String memberEmail);


    default MemberVO toVO(MemberDTO memberDTO) {
        return MemberVO.builder()
                .id(memberDTO.getId())
                .memberName(memberDTO.getMemberName())
                .memberPhone(memberDTO.getMemberPhone())
                .memberEmail(memberDTO.getMemberEmail())
                .memberSocialUrl(memberDTO.getMemberSocialUrl())
                .memberBirth(memberDTO.getMemberBirth())
                .memberGender(memberDTO.getMemberGender())
                .memberMbti(memberDTO.getMemberMbti())
                .memberPassword(memberDTO.getMemberPassword())
                .memberStatus(memberDTO.getMemberStatus())
                .memberProvider(memberDTO.getMemberProvider())
                .kakaoImgUrl(memberDTO.getKakaoImgUrl())
                .memberSocialEmail(memberDTO.getMemberSocialEmail())
                .memberDescription(memberDTO.getMemberDescription())
                .memberRole(memberDTO.getMemberRole())
                .createdDatetime(memberDTO.getCreatedDatetime())
                .updatedDatetime(memberDTO.getUpdatedDatetime())
                .build();
    }

    default AddressVO toVO(AddressDTO addressDTO) {
        return AddressVO.builder()
                .id(addressDTO.getId())
                .address(addressDTO.getAddress())
                .addressDetail(addressDTO.getAddressDetail())
                .addressZipCode(addressDTO.getAddressZipCode())
                .createdDatetime(addressDTO.getCreatedDatetime())
                .updatedDatetime(addressDTO.getUpdatedDatetime())
                .build();
    }
}
