package com.example.crewstation.service.gift;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.gift.GiftCriteriaDTO;
import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.util.Search;

import java.util.List;

public interface GiftService {

//    기프로 리스트 조회
    List<GiftDTO> getGift(int limit);

//    기프트 검색 목록
    GiftCriteriaDTO getGifts(Search search,  CustomUserDetails customUserDetails);
}
