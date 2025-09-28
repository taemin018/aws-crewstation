package com.example.crewstation.mapper.guest;

import com.example.crewstation.domain.guest.GuestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuestMapper {
//  게스트 회원 추가
    public void insert(GuestVO guestVO);
}
