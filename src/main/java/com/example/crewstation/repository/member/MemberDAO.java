package com.example.crewstation.repository.member;

import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.member.MemberProfileDTO;
import com.example.crewstation.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberDAO {
    private final MemberMapper memberMapper;
    //    회원가입
    public void save(MemberVO memberVO){
        memberMapper.insert(memberVO);
    }

//  이메일 중복 검사
    public boolean checkEmail(String memberEmail){
        return memberMapper.selectEmail(memberEmail);
    }

    //    로그인
    public Optional<MemberDTO> findForLogin(MemberDTO memberDTO){
        return memberMapper.selectForLogin(memberDTO);
    }

    //    이메일로 회원 조회
    public Optional<MemberDTO> findByMemberEmail(String memberEmail){
        return memberMapper.selectMemberByMemberEmail(memberEmail);
    }

//    sns 회원가입
    public void saveSns(MemberVO memberVO){
    memberMapper.insertSns(memberVO);
}

//    sns 회원 조회
    public Optional<MemberDTO> findBySnsEmail(String snsEmail){
        return memberMapper.selectMemberBySnsEmail(snsEmail);
    }

    //  게스트 추가
    public void saveGuest(MemberDTO memberDTO){
        memberMapper.insertGuest(memberDTO);
    }

//  멤버 프로필 조회
    public Optional<MemberProfileDTO> selectProfileById(Long memberId) {
        return memberMapper.selectProfileById(memberId);
    }

//    비밀번호 업데이트
    public void updatePassword(String memberEmail, String memberPassword) {
        memberMapper.updatePassword(memberEmail, memberPassword);
    }

//    별점 등록 시 케미지수 업데이트
    public void updateChemistryScore(Long sellerId, int rating) {
        memberMapper.updateChemistryScore(sellerId, rating);
    }

}
