package com.example.crewstation.service.member;

import com.example.crewstation.domain.address.AddressVO;
import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.domain.file.member.MemberFileVO;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.dto.file.member.MemberFileDTO;
import com.example.crewstation.dto.member.AddressDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.member.MemberProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface MemberService {
//    회원가입
    public void join(MemberDTO memberDTO, MultipartFile multipartFile);

//    이메일 중복 검사
    public boolean checkEmail(String memberEmail);

//    로그인
    public MemberDTO login(MemberDTO memberDTO);

//    회원 정보 조회
    public MemberDTO getMember(String memberEmail, String provider);

// sns 가입
    public void joinSns(MemberDTO memberDTO, MultipartFile multipartFile);

//
    public Optional<MemberProfileDTO> getMemberProfile(Long memberId);



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
                .socialImgUrl(memberDTO.getSocialImgUrl())
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
                .memberId(addressDTO.getMemberId())
                .createdDatetime(addressDTO.getCreatedDatetime())
                .updatedDatetime(addressDTO.getUpdatedDatetime())
                .build();
    }

    default FileVO toVO(FileDTO fileDTO) {
        return FileVO.builder()
                .id(fileDTO.getId())
                .fileOriginName(fileDTO.getFileOriginName())
                .fileName(fileDTO.getFileName())
                .filePath(fileDTO.getFilePath())
                .fileSize(fileDTO.getFileSize())
                .createdDatetime(fileDTO.getCreatedDatetime())
                .updatedDatetime(fileDTO.getUpdatedDatetime())
                .build();
    }

    default MemberFileVO toVO(MemberFileDTO memberfileDTO) {
        return MemberFileVO.builder()
                .fileId(memberfileDTO.getFileId())
                .memberId(memberfileDTO.getMemberId())
                .build();
    }
}

