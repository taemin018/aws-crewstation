package com.example.crewstation.controller.purchase;

import com.example.crewstation.auth.JwtTokenProvider;
import com.example.crewstation.common.exception.PurchaseNotFoundException;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.service.purchase.PurchaseService;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/gifts/**")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {
    private final PurchaseService purchaseService;
    //    임시 로그인
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @GetMapping("")
    public String list(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("userDetails {}", userDetails);
        return "gift-shop/list";
    }

    @GetMapping("{postId}")
    public String detail(@PathVariable Long postId, Model model) {

//        임시 로그인
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test@ac.kr");
        memberDTO.setMemberPassword("1234");
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memberDTO.getMemberEmail(), memberDTO.getMemberPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtTokenProvider.createAccessToken(((UserDetails) authentication.getPrincipal()).getUsername());
            String refreshToken = jwtTokenProvider.createRefreshToken(((UserDetails) authentication.getPrincipal()).getUsername());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            log.info("tokens {}", tokens);
        } catch(AuthenticationException e) {
                log.error("AuthenticationException", e);
        }


        Optional<PurchaseDetailDTO> purchase = purchaseService.getPurchase(postId);

        model.addAttribute("purchase", purchase.orElseThrow(PurchaseNotFoundException::new));
        return "gift-shop/detail";
    }


}
