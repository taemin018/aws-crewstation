package com.example.crewstation.service.gift;

import com.example.crewstation.dto.gift.GiftDTO;

import java.util.List;

public interface GiftService {

//    기프로 리스트 조회
    List<GiftDTO> getGift(int limit);
}
