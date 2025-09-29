package com.example.crewstation.service.banner;

import com.example.crewstation.dto.banner.BannerDTO;

import java.util.List;

public interface BannerService {

//    배너 리스트 조회
    List<BannerDTO> getBanners();
}
