package com.example.crewstation.controller.member;

import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/member/web/**")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

//    web 회원가입
    @GetMapping("join")
    public String join(MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);

        return "member/web/join";
    }

    @PostMapping("join")
    public RedirectView join(@ModelAttribute("memberDTO") MemberDTO memberDTO, @RequestParam("file")MultipartFile multipartFile) {
        memberService.join(memberDTO, multipartFile);

        return new RedirectView("/member/web/login");
    }

    //    web 로그인
    @GetMapping("login")
    public String login(MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        return "member/web/login";
    }

}
