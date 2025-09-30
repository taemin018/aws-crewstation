package com.example.crewstation.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.mapper.banner.BannerMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MapperTests {

    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private BannerDTO bannerDTO;

    @Test
    public void getBannerTest() {
        List<BannerDTO> banners = bannerMapper.getBanners(5);
        log.info("banners: {}", banners);
    }
}
