package com.example.crewstation.controller.post;


import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post/**")
@Slf4j
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;

    @PostMapping("report")
    public ResponseEntity<String> reportPurchase(@RequestBody ReportDTO reportDTO) {
        try{
            postService.report(reportDTO);
            return ResponseEntity.ok().body("신고 완료되었습니다.");
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
