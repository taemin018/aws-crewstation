//package com.example.crewstation.controller.member;
//
//import com.example.crewstation.service.member.MemberService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/member/**")
//@RequiredArgsConstructor
//@Slf4j
//public class MembersApiController {
////    이메일 중복 검사
//    private final MemberService memberService;
//
//    @PostMapping("email-check")
//    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
//       boolean check = memberService.checkEmail(email);
//
//       return ResponseEntity.ok(check);
//
//    }
//
//
//}
