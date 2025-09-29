package com.example.crewstation.controller.diary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class DiaryController {

    // 마이페이지 -> 내가 좋아요한 일기 목록 화면
    @GetMapping("/mypage/my-activities/liked")
    public String loadLikedDiariesPage() {
        log.info("마이페이지 - 좋아요한 일기 화면 요청");
        return "mypage/my-activities/liked";
    }

    // 마이페이지 -> 내가 댓글 단 일기 목록 화면
    @GetMapping("/mypage/my-activities/reply")
    public String loadReplyDiariesPage() {
        log.info("마이페이지 - 내가 댓글 단 일기 화면 요청");
        return "/mypage/my-activities/reply";
    }
}