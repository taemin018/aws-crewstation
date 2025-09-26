package com.example.crewstation.service.member;

import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.MemberDTO;

public interface MemberService {
//    회원가입
    public void join(MemberDTO memberDTO);


//    로그인


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
}
