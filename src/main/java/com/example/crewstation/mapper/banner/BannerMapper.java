package com.example.crewstation.mapper.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BannerMapper {

//    배너 리스트 조회
    public List<BannerDTO> getBanners(@Param("limit") int limit);
}
