package com.example.crewstation.controller.member;

import com.example.crewstation.auth.JwtTokenProvider;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.guest.GuestService;
import com.example.crewstation.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/member/**")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final GuestService guestService;
    private final JwtTokenProvider jwtTokenProvider;

//    web 회원가입
    @GetMapping("web/join")
    public String join(MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);

        return "member/web/join";
    }

    @PostMapping("web/join")
    public RedirectView join(@ModelAttribute("memberDTO") MemberDTO memberDTO, @RequestParam("file")MultipartFile multipartFile) {
        memberService.join(memberDTO, multipartFile);

        return new RedirectView("/member/web/login");
    }

    //    web 로그인
    @GetMapping("web/login")
    public String login(MemberDTO memberDTO, GuestDTO guestDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("guestDTO", guestDTO);
        return "member/web/login";
    }

    //    mobile 회원가입
    @GetMapping("mobile/join")
    public String mobileJoin(MemberDTO memberDTO, GuestDTO guestDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("guestDTO", guestDTO);

        return "member/mobile/join";
    }

    @PostMapping("mobile/join")
    public RedirectView mobileJoin(@ModelAttribute("memberDTO") MemberDTO memberDTO, @RequestParam("file")MultipartFile multipartFile) {
        memberService.join(memberDTO, multipartFile);

        return new RedirectView("/member/mobile/login");
    }

    //    mobile 로그인
    @GetMapping("mobile/login")
    public String mobileLogin(MemberDTO memberDTO, GuestDTO guestDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("guestDTO", guestDTO);
        return "member/mobile/login";
    }

// guest

    @PostMapping("web/login")
    public RedirectView login(@ModelAttribute("guestDTO") GuestDTO guestDTO) {
        guestService.login(guestDTO);
        log.info(guestService.login(guestDTO).toString());

        return new RedirectView("/guest/purchase-detail");
    }

    @PostMapping("mobile/login")
    public RedirectView mobileLogin(@ModelAttribute("guestDTO") GuestDTO guestDTO) {
        guestService.login(guestDTO);


        return new RedirectView("/member/mobile/login");
    }

//    web sns 회원가입
    @GetMapping("web/sns/join")
    public String snsJoin(@CookieValue(value = "memberSocialEmail", required = false) String memberSocialEmail,
                          @CookieValue(value = "profile", required = false) String socialProfile,
                          @CookieValue(value = "name", required = false) String memberName,
                          MemberDTO memberDTO, Model model) {
        memberDTO.setMemberSocialEmail(memberSocialEmail);
        memberDTO.setSocialImgUrl(socialProfile);
        memberDTO.setMemberName(memberName);

        model.addAttribute("memberDTO", memberDTO);

        return "member/web/sns/join";
    }

    @PostMapping("web/sns/join")
    public RedirectView join(@CookieValue(value = "role", required = false) String role,
                             @CookieValue(value = "provider", required = false) String provider, MemberDTO memberDTO,
                             @RequestParam("file")MultipartFile multipartFile) {
        memberDTO.setMemberRole(role.equals("ROLE_MEMBER") ? MemberRole.MEMBER : MemberRole.ADMIN);
        memberDTO.setMemberProvider(MemberProvider.valueOf(provider.toUpperCase()));
        memberService.joinSns(memberDTO, multipartFile);

        jwtTokenProvider.createAccessToken(memberDTO.getMemberSocialEmail(), provider);
        jwtTokenProvider.createRefreshToken(memberDTO.getMemberSocialEmail(), provider);

        return new RedirectView("/");
    }

    //    mobile sns 회원가입
    @GetMapping("mobile/sns/join")
    public String mobileJoin(@CookieValue(value = "memberSocialEmail", required = false) String memberSocialEmail,
                          @CookieValue(value = "profile", required = false) String socialProfile,
                          @CookieValue(value = "name", required = false) String memberName,
                          MemberDTO memberDTO, Model model) {
        memberDTO.setMemberSocialEmail(memberSocialEmail);
        memberDTO.setSocialImgUrl(socialProfile);
        memberDTO.setMemberName(memberName);

        model.addAttribute("memberDTO", memberDTO);

        return "member/mobile/sns/join";
    }

    @PostMapping("mobile/sns/join")
    public RedirectView mobileJoin(@CookieValue(value = "role", required = false) String role,
                             @CookieValue(value = "provider", required = false) String provider, MemberDTO memberDTO,
                             @RequestParam("file")MultipartFile multipartFile) {
        memberDTO.setMemberRole(role.equals("ROLE_MEMBER") ? MemberRole.MEMBER : MemberRole.ADMIN);
        memberDTO.setMemberProvider(MemberProvider.valueOf(provider.toUpperCase()));
        memberService.joinSns(memberDTO, multipartFile);

        jwtTokenProvider.createAccessToken(memberDTO.getMemberSocialEmail(), provider);
        jwtTokenProvider.createRefreshToken(memberDTO.getMemberSocialEmail(), provider);

        return new RedirectView("/");
    }




//    비밀번호 찾기
//    web
    @GetMapping("web/password")
    public String password(MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        return "member/web/forgot-password";
    }
}
