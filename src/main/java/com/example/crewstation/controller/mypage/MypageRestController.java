package com.example.crewstation.controller.mypage;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
import com.example.crewstation.dto.member.MySaleListDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.service.guest.GuestService;
import com.example.crewstation.service.member.MemberService;
import com.example.crewstation.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage/**")
public class MypageRestController {

    private final GuestService guestService;
    private final MemberService memberService;
    private final PurchaseService purchaseService;

    // 구매 상세
//    @GetMapping("/purchase-detail/{postId}")
//    public ResponseEntity<PurchaseDetailDTO> getOrderDetail(@PathVariable Long postId) {
//        Optional<PurchaseDetailDTO> orderDetailOpt = purchaseService.getPurchaseDetail(postId);
//
//        if (orderDetailOpt.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(orderDetailOpt.get());
//    }
//
//    // 결제 상태 업데이트
//    @PutMapping("/purchase-detail/{purchaseId}/status")
//    public ResponseEntity<Void> updatePaymentStatus(
//            @PathVariable Long purchaseId,
//            @RequestParam PaymentPhase paymentPhase) {
//
//        Optional<PurchaseDetailDTO> orderOpt = purchaseService.getPurchaseDetail(purchaseId);
//
//        if (orderOpt.isEmpty()) {
//            log.warn("Order not found: {}", purchaseId);
//            return ResponseEntity.notFound().build();
//        }
//
//        PurchaseDetailDTO order = orderOpt.get();
//        purchaseService.updatePaymentStatus(order.getPurchaseId(), paymentPhase);
//        return ResponseEntity.ok().build();
//    }

//  판매 내역 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<MySaleListDTO>> getMySaleList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long memberId = customUserDetails.getId();
        List<MySaleListDTO> saleList = memberService.getMySaleList(memberId);

        if (saleList == null || saleList.isEmpty()) {
            log.info("No sale list found for memberId={}", memberId);
            return ResponseEntity.noContent().build();
        }

        log.info("Found {} sale items for memberId={}", saleList.size(), memberId);
        return ResponseEntity.ok(saleList);
    }

    //   판매 상태 업데이트
    @PutMapping("/status/{postId}")
    public ResponseEntity<Void> updateSaleStatus(
            @PathVariable Long postId,
            @RequestParam String paymentPhase,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long memberId = customUserDetails.getId(); // 로그인 사용자 정보
        log.info("PUT /api/mypage/sale/status/{} by memberId={} with phase={}",
                postId, memberId, paymentPhase);

        // TODO: 서비스 로직 추가
        // memberService.updateSaleStatus(memberId, postId, PaymentPhase.valueOf(paymentPhase));

        return ResponseEntity.ok().build();
    }



}