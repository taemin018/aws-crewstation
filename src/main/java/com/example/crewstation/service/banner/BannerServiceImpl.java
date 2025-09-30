package com.example.crewstation.service.banner;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.repository.banner.BannerDAO;
import com.example.crewstation.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerServiceImpl implements BannerService {
    private final BannerDAO bannerDAO;
    private final S3Service s3Service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "banners", key = "'banners_' + #post_id")
    @LogReturnStatus
    public List<BannerDTO> getBanners(int limit) {
        List<BannerDTO> banners = bannerDAO.getBanners(limit);
        banners.forEach(banner -> {
            banner.setFilePath(s3Service.getPreSignedUrl(banner.getFilePath(),
                    Duration.ofMinutes(5)));
        });
        return banners;
    }
}
