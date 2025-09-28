package com.example.crewstation.controller.main;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.service.accompany.AccompanyService;
import com.example.crewstation.service.banner.BannerService;
import com.example.crewstation.service.crew.CrewService;
import com.example.crewstation.service.diary.DiaryService;
import com.example.crewstation.service.gift.GiftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class MainPageController {
    private final CrewService crewService;
    private final DiaryService diaryService;
    private final BannerService bannerService;
    private final GiftService giftService;
    private final AccompanyService accompanyService;

    @GetMapping
    public String getMainPage(CrewDTO crewDTO,
                              DiaryDTO diaryDTO,
                              BannerDTO bannerDTO,
                              GiftDTO giftDTO,
                              AccompanyDTO accompanyDTO,
                              Model model) {

        List<CrewDTO> crews = crewService.getCrews(crewDTO);
        List<DiaryDTO> diaries = diaryService.selectDiaryList(diaryDTO);
        List<BannerDTO> banners = bannerService.getBanners(bannerDTO);
        List<GiftDTO> gifts = giftService.getGift(4);
        List<AccompanyDTO> accompanies = accompanyService.getAccompanies(4);



        model.addAttribute("crews", crews);
        model.addAttribute("diaries", diaries);
        model.addAttribute("banners", banners);
        model.addAttribute("gifts", gifts);
        model.addAttribute("accompanies", accompanies);

        return "main-page/main";
    }
}
