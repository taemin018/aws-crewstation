//package com.example.crewstation.repository.member;
//
//import com.example.crewstation.domain.member.MemberVO;
//import com.example.crewstation.dto.member.MemberDTO;
//import com.example.crewstation.mapper.member.MemberMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//@RequiredArgsConstructor
//@Slf4j
//public class MemberDAO {
//    private final MemberMapper memberMapper;
//    //    회원가입
//    public void save(MemberVO memberVO){
//        memberMapper.insert(memberVO);
//    }
//
////  이메일 중복 검사
//    public boolean checkEmail(String memberEmail){
//        return memberMapper.selectEmail(memberEmail);
//    }
//
//    //    로그인
//    public Optional<MemberDTO> findForLogin(MemberDTO memberDTO){
//        return memberMapper.selectForLogin(memberDTO);
//    }
//
//    //    이메일로 회원 조회
//    public Optional<MemberDTO> findByMemberEmail(String memberEmail){
//        return memberMapper.selectMemberByMemberEmail(memberEmail);
//    }
//    public Optional<MemberDTO> findBySnsEmail(String snsEmail){
//        return memberMapper.selectMemberBySnsEmail(snsEmail);
//    }
//}
