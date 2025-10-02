package com.example.crewstation.mapper.member;

import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.member.MemberProfileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    //    회원가입
    public void insert(MemberVO memberVO);

    //    이메일 중복 검사
    public boolean selectEmail(String memberEmail);

    //    sns 회원가입
    public void insertSns(MemberVO memberVO);

    //    로그인
    public Optional<MemberDTO> selectForLogin(MemberDTO memberDTO);

    //    이메일로 조회
    public Optional<MemberDTO> selectMemberByMemberEmail(String memberEmail);

//    sns 조회
    public Optional<MemberDTO> selectMemberBySnsEmail(String snsEmail);

//  게스트 추가
    public void insertGuest(MemberDTO memberDTO);

//  멤버 프로필 조회용
    public Optional<MemberProfileDTO> selectProfileById(@Param("memberId") Long memberId);

//    비밀번호 업데이트
    public void updatePassword(@Param("memberEmail") String memberEmail,@Param("memberPassword") String memberPassword);

//   별점 등록 시 케미지수 업데이트
    public void updateChemistryScore(@Param("sellerId") Long sellerId, @Param("rating") int rating);
}


