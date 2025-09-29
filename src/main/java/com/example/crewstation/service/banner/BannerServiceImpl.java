package com.example.crewstation.service.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.repository.banner.BannerDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerServiceImpl implements BannerService {
    private final BannerDAO bannerDAO;

    @Override
    public List<BannerDTO> getBanners() {
        return bannerDAO.getBanners();
    }
}
