package com.example.crewstation.controller.reply;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.reply.ReplyCriteriaDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import com.example.crewstation.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies/**")
@Slf4j
@RequiredArgsConstructor
public class ReplyRestController {
    private final ReplyService replyService;
    @GetMapping("{postId}")
    public ResponseEntity<ReplyCriteriaDTO> getReplies(
            @RequestParam int page,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ReplyCriteriaDTO replies = replyService.getReplies(page, postId, customUserDetails);
        return ResponseEntity.ok(replies);
    }
}
