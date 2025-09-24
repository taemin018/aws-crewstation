package com.example.crewstation.controller;

import com.example.crewstation.dto.member.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/web/**")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

//    web 회원가입
    @GetMapping("join")
    public String join(MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        return "member/web/join";
    }

//    @PostMapping("join")
//    public RedirectView join(MemberDTO memberDTO) {
//        memberService.join(memberDTO);
//        return new RedirectView("member/login");
//    }

    //    web 로그인
    @GetMapping("login")
    public String login(MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        return "member/web/login";
    }

}
