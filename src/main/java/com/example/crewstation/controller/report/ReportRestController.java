package com.example.crewstation.controller.report;


import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report/**")
@Slf4j
@RequiredArgsConstructor
public class ReportRestController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<String> reportPurchase(@RequestBody ReportDTO reportDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try{
            reportDTO.setMemberId(userDetails.getId());
            reportService.report(reportDTO);
            return ResponseEntity.ok().body("신고 완료되었습니다.");
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("replies")
    public ResponseEntity<String> reportReply(@RequestBody ReportDTO reportDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try{
            reportDTO.setMemberId(userDetails.getId());
            reportService.reportReply(reportDTO);
            log.info(reportDTO.toString());
            return ResponseEntity.ok().body("신고 완료되었습니다.");
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
