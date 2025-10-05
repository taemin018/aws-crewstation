package com.example.crewstation.controller.diary;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.DiaryDetailDTO;
import com.example.crewstation.dto.file.tag.PostDiaryDetailTagDTO;
import com.example.crewstation.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
@RequestMapping("/diaries/**")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping
    public String list(){
        return "diary/list";
    }

    @GetMapping("write")
    public String goWriteForm(@RequestParam Long path,@RequestParam(required = false) Long crew, Model model){
        model.addAttribute("path",path);
        model.addAttribute("crew",crew);
        return "mypage/my-diary/write";
    }
    @PostMapping("write")
    public RedirectView write(PostDiaryDetailTagDTO request, @AuthenticationPrincipal CustomUserDetails customUserDetails){
//        request.setMemberId(customUserDetails.getId());
        request.setMemberId(1L);
        diaryService.write(request);
        log.info("{}",request);
        return new RedirectView("/diaries");
    }


    @GetMapping("detail/{postId}")
    public String detail(@PathVariable Long postId, Model model,@AuthenticationPrincipal CustomUserDetails customUserDetails){
        DiaryDetailDTO diary = diaryService.getDiary(postId, customUserDetails);
        model.addAttribute("diary",diary);

        return "diary/detail";
    }
}