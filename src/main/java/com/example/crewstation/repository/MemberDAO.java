package com.example.crewstation.repository;

import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberDAO {
    private final MemberMapper memberMapper;
    //    회원가입
    public void save(MemberVO memberVO){
        memberMapper.insert(memberVO);
    }
}
