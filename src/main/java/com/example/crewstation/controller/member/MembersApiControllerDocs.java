package com.example.crewstation.controller.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Diary", description = "Diary API")
public interface MembersApiControllerDocs {

    @Operation(summary = "이메일 중복 체크",
            description = "결과 값으로 이메일 중복 체크",
            parameters = {
                    @Parameter(name = "email", description = "회원가입 시 입력한 이메일")
            }
    )
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email);

    @Operation(summary = "메일 보내기",
            description = "일반 회원과 SNS 회원의 로그인 정보를 모두 제거하고 로그아웃 처리한다.",
            parameters = {
                    @Parameter(name = "token", description = "쿠키에 있는 access token이 들어온다.")
            }
    )
    public void logout(@CookieValue(value = "accessToken", required = false) String token);

//    @PostMapping("/send-email")
//    public ResponseEntity<Map<String, String>> sendEmail(@RequestParam("email") String email) {
//        try {
//            String code = mailService.sendMail(email);
//            return ResponseEntity.ok(Map.of("code", code));
//
//        } catch (jakarta.mail.MessagingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "메일 전송 실패"));
//
//        }
//    }
//
//    //    핸드폰 인증 메세지 발송
//    @PostMapping("/phone-check")
//    public ResponseEntity<Map<String, String>> checkPhone(@RequestParam String phone) {
//        String code = joinSmsService.send(phone);
//        log.info("code: {}", code);
//        return ResponseEntity.ok(Map.of("code", code));
//    }
}
