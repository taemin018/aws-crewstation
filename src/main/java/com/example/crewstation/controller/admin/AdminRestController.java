package com.example.crewstation.controller.admin;

import com.example.crewstation.dto.member.MemberAdminStatics;
import com.example.crewstation.dto.member.MemberCriteriaDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.member.MemberService;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin**")
@RequiredArgsConstructor
@Slf4j
public class AdminRestController {

    private final MemberService memberService;

//    관리자 회원 목록
    @PostMapping("/members")
    public ResponseEntity<MemberCriteriaDTO> getMembers(@RequestBody Search search) {
        return ResponseEntity.ok(memberService.getMembers(search));
    }

//    관리자 회원 상세
    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberDTO> getMemberDetails(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMemberDetail(memberId));
    }

//    관리자 메인 회원 통계
    @GetMapping("")
    public ResponseEntity<MemberAdminStatics> getStatics() {
        MemberAdminStatics statics = memberService.getStatics();
        return ResponseEntity.ok(statics);

    }



}
