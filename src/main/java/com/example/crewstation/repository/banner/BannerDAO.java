package com.example.crewstation.repository.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.mapper.banner.BannerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BannerDAO {
    private final BannerMapper bannerMapper;

//    배너 리스트 조회하기
    public List<BannerDTO> getBanners(@Param("limit") int limit) {
        return bannerMapper.getBanners(limit);
    }

//    배너 추가
    public void insertBanner(BannerDTO bannerDTO) {
        bannerMapper.insertBanner(bannerDTO);
    }

//    배너 파일 추가
    public void insertBannerFile(BannerDTO bannerDTO) {
        bannerMapper.insertBannerFile(bannerDTO);
    }

//    배너 수정
    public void updateBannerFile(BannerDTO bannerDTO) {
        bannerMapper.updateBannerFile(bannerDTO);
    }

//    배너 삭제
    public void deleteBanner(@Param("bannerId") Long bannerId) {
        bannerMapper.deleteBanner(bannerId);
    }


}
