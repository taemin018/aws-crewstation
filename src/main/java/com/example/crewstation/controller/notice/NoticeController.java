package com.example.crewstation.controller.notice;

import com.example.crewstation.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notice/**")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("notice")
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("noticeCriteriaDTO", noticeService.getNotices(page));
        return "notice/notice";
    }
}
