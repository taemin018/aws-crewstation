package com.example.crewstation.controller.admin;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.domain.notice.NoticeDetailVO;
import com.example.crewstation.domain.notice.NoticeVO;
import com.example.crewstation.dto.member.MemberAdminStatics;
import com.example.crewstation.dto.member.MemberCriteriaDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.notice.NoticeCriteriaDTO;
import com.example.crewstation.dto.notice.NoticeWriteRequest;
import com.example.crewstation.service.member.MemberService;
import com.example.crewstation.service.notice.NoticeDetailService;
import com.example.crewstation.service.notice.NoticeService;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminRestController {

    private final MemberService memberService;
    private final NoticeService noticeService;
    private final NoticeDetailService noticeDetailService;

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
    @GetMapping({"", "/"})
    public ResponseEntity<MemberAdminStatics> getStatics() {
        MemberAdminStatics statics = memberService.getStatics();
        return ResponseEntity.ok(statics);

    }
//  공지사항 목록
    @GetMapping("/notices")
    public ResponseEntity<NoticeCriteriaDTO> getAdminNotices(@RequestParam(defaultValue = "1") int page) {
        int safePage = Math.max(1, page);
        return ResponseEntity.ok(noticeService.getAdminNotices(safePage));
    }

//    공지사항 작성
    @PostMapping("/notices")
    public ResponseEntity<?> createNotice(@AuthenticationPrincipal CustomUserDetails admin,
                                          @RequestBody NoticeWriteRequest req) {
        Long memberId = (admin != null) ? admin.getId()
                : (req.getMemberId() != null ? req.getMemberId() : 1L);
        Long id = noticeService.insertNotice(memberId, req.getTitle(), req.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

//    공지사항 상세
    @GetMapping("notice/{id}")
    public ResponseEntity<NoticeDetailVO> noticeDetail(@PathVariable Long id) {
        NoticeDetailVO notice = noticeDetailService.getDetail(id);
        return ResponseEntity.ok(notice);
    }









}
