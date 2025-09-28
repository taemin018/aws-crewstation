package com.example.crewstation.repository.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.mapper.banner.BannerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BannerDAO {
    private final BannerMapper bannerMapper;

//    배너 리스트 조회하기
    public List<BannerDTO> getBanners(BannerDTO bannerDTO) {
        return bannerMapper.getBanners(bannerDTO);
    }
}
