package com.example.crewstation.controller.notice;

import com.example.crewstation.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
@Slf4j   // ✅ log 객체 자동 생성
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/notice")
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        log.info("📌 /notice/notice 요청 들어옴, page={}", page);


        model.addAttribute("noticeCriteriaDTO", noticeService.getNotices(page));
        log.info("📌 noticeCriteriaDTO 추가됨, 데이터={}", model.getAttribute("noticeCriteriaDTO"));

        return "notice/notice";
    }
}
