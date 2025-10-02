package com.example.crewstation.controller.search;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.dto.diary.DiaryCriteriaDTO;
import com.example.crewstation.service.crew.CrewService;
import com.example.crewstation.service.diary.DiaryService;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search/**")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final CrewService crewService;
    private final DiaryService diaryService;

    @GetMapping()
    public String getSearch(Search search,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails,
                            Model model) {

        List<CrewDTO> crews = crewService.getCrews();
        DiaryCriteriaDTO diaries = diaryService.countDiaryImg(search,customUserDetails);


        model.addAttribute("crews", crews);
        model.addAttribute("diaries", diaries);



        return "search/list";


    }
}







