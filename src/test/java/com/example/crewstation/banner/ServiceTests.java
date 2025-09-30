package com.example.crewstation.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.service.banner.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ServiceTests {
    @Autowired
    private BannerService bannerService;

    @Test
    public void getBannerTest(){
        List<BannerDTO> banners = bannerService.getBanners(4);
        log.info("banners:{}",banners);
    }
}
