package com.example.crewstation.controller.member;

import com.example.crewstation.dto.member.MemberProfileDTO;
import com.example.crewstation.service.mail.MailService;
import com.example.crewstation.service.member.MemberService;
import com.example.crewstation.service.sms.JoinSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/member/**")
@RequiredArgsConstructor
@Slf4j
public class MembersApiController {
    //    이메일 중복 검사
    private final MemberService memberService;
    private final MailService mailService;
    private final JoinSmsService joinSmsService;

    @PostMapping("email-check")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean check = memberService.checkEmail(email);

        return ResponseEntity.ok(check);

    }


    @GetMapping("/{memberId}")
    public ResponseEntity<MemberProfileDTO> getMemberProfile(@PathVariable Long memberId) {
        return memberService.getMemberProfile(memberId)
                .map(ResponseEntity::ok)      // 값이 있으면 200ok
                .orElse(ResponseEntity.notFound().build()); // 없으면 404
    }

    @PostMapping("/send-email")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestParam("email") String email) {
        try {
            String code = mailService.sendMail(email);
            return ResponseEntity.ok(Map.of("code", code));

        } catch (jakarta.mail.MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "메일 전송 실패"));

        }
    }

//    핸드폰 인증 메세지 발송
    @PostMapping("/phone-check")
    public ResponseEntity<Map<String, String>> checkPhone(@RequestParam String phone) {
        String code = joinSmsService.send(phone);
        return ResponseEntity.ok(Map.of("code", code));
    }

//    로그아웃
}
