package com.example.crewstation.mapper.member;

import com.example.crewstation.domain.member.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    //    회원가입
    public void insert(MemberVO memberVO);
}
