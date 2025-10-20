package com.example.crewstation.controller.mypage;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.member.MyPurchaseDetailDTO;
import com.example.crewstation.dto.member.MySaleListDTO;
import com.example.crewstation.service.guest.GuestService;
import com.example.crewstation.service.member.MemberService;
import com.example.crewstation.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage/**")
public class MypageRestController {

    private final MemberService memberService;
    private final PurchaseService purchaseService;

    // 구매 상세 조회
    @GetMapping("/purchase-detail/{paymentStatusId}")
    public ResponseEntity<MyPurchaseDetailDTO> getPurchaseDetail(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long paymentStatusId) {

        Long memberId = customUserDetails.getId();
        log.info("GET /purchase-detail called by memberId={}, paymentStatusId={}", memberId, paymentStatusId);

        MyPurchaseDetailDTO orderDetail = purchaseService.getMemberOrderDetails(memberId, paymentStatusId);

        if (orderDetail == null) {
            log.warn("Order not found for memberId={}, paymentStatusId={}", memberId, paymentStatusId);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderDetail);
    }

    // 결제 상태 업데이트
    @PutMapping("/purchase-detail/{paymentStatusId}/status")
    public ResponseEntity<Void> updatePaymentStatus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long paymentStatusId,
            @RequestParam PaymentPhase paymentPhase) {

        Long memberId = customUserDetails.getId();
        log.info("PUT /purchase-detail/{}/status called by memberId={}, phase={}", paymentStatusId, memberId, paymentPhase);

        MyPurchaseDetailDTO order = purchaseService.getMemberOrderDetails(memberId, paymentStatusId);
        if (order == null) {
            log.warn("Order not found for memberId={}, paymentStatusId={}", memberId, paymentStatusId);
            return ResponseEntity.notFound().build();
        }

        purchaseService.updatePaymentStatus(paymentStatusId, paymentPhase);
        log.info("Payment status updated for paymentStatusId={}, newStatus={}", paymentStatusId, paymentPhase);

        return ResponseEntity.ok().build();
    }


    //   판매 상태 업데이트
    @PutMapping("/status/{postId}")
    public ResponseEntity<Void> updateSaleStatus(
            @PathVariable Long postId,
            @RequestParam PaymentPhase paymentPhase,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long memberId = customUserDetails.getId(); // 로그인 사용자 정보
        log.info("PUT /api/mypage/sale/status/{} by memberId={} with phase={}",
                postId, memberId, paymentPhase);

        // TODO: 서비스 로직 추가
        // memberService.updateSaleStatus(memberId, postId, PaymentPhase.valueOf(paymentPhase));

        return ResponseEntity.ok().build();
    }



}