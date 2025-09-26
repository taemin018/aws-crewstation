package com.example.crewstation.controller.member;

import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/member/web/**")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final AddressService addressService;

//    web 회원가입
    @GetMapping("join")
    public String join(MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);

        return "member/web/join";
    }

    @PostMapping("join")
    public RedirectView join(MemberDTO memberDTO) {
//        memberService.join(memberDTO);
//        AddressDTO addressDTO = memberDTO.getAddressDTO();
//        addressDTO.setMemberId( memberDTO.getId());
//        addressService.join(addressDTO);

        return new RedirectView("/");
    }

    //    web 로그인
    @GetMapping("login")
    public String login(MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        return "member/web/login";
    }

}
