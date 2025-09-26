package com.example.crewstation.controller.main;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.service.crew.CrewService;
import com.example.crewstation.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class MainPageController {
    private final CrewService crewService;
    private final DiaryService diaryService;

    @GetMapping
    public String getMainPage(CrewDTO crewDTO,
                              DiaryDTO diaryDTO,
                              Model model) {
        model.addAttribute(crewService.getCrews(crewDTO));
        model.addAttribute(diaryService.selectDiaryList(diaryDTO));

        return "main/main";
    }
}
