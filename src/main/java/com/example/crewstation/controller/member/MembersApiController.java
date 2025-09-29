package com.example.crewstation.controller.member;

import com.example.crewstation.dto.member.MemberProfileDTO;
import com.example.crewstation.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/**")
@RequiredArgsConstructor
@Slf4j
public class MembersApiController {
//    이메일 중복 검사
    private final MemberService memberService;

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

}
