package com.example.crewstation.mapper.gift;

import com.example.crewstation.dto.gift.GiftDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GiftMapper {

//    기프트 리스트 조회
    public List<GiftDTO> getGift(@Param("limit") int limit);
}
