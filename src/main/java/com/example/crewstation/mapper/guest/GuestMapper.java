package com.example.crewstation.mapper.guest;

import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.guest.GuestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface GuestMapper {
//  게스트 회원 추가
    public void insert(GuestVO guestVO);

//    게스트 로그인
    public Optional<GuestDTO> select(GuestDTO guestDTO);
}
