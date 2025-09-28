package com.example.crewstation.repository.guest;

import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.mapper.guest.GuestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GuestDAO {
    private final GuestMapper guestMapper;

    //  게스트 회원 추가
    public void save(GuestVO guestVO) {
        guestMapper.insert(guestVO);
    }
}
