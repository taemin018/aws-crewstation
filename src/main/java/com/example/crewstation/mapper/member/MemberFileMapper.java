package com.example.crewstation.mapper.member;

import com.example.crewstation.dto.file.member.MemberFileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberFileMapper {
    //    회원가입 시 프로필  insert
    public void insert(MemberFileDTO memberFileDTO);

}
