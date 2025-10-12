package com.example.crewstation.controller.mypage;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
import com.example.crewstation.dto.member.MySaleListDTO;
import com.example.crewstation.service.guest.GuestService;
import com.example.crewstation.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage/**")
public class MypageRestController {

    private final GuestService guestService;
    private final MemberService memberService;

    // 주문번호로 단건 조회
    @GetMapping("/purchase-detail/{guestOrderNumber}")
    public ResponseEntity<GuestOrderDetailDTO> getOrderDetail(@PathVariable String guestOrderNumber) {
        GuestOrderDetailDTO orderDetail = guestService.getOrderDetail(guestOrderNumber);
        return ResponseEntity.ok(orderDetail);
    }

    // 결제 상태 업데이트
    @PutMapping("/purchase-detail/{guestOrderNumber}/status")
    public ResponseEntity<Void> updatePaymentStatus(
            @PathVariable String guestOrderNumber,
            @RequestParam PaymentPhase paymentPhase) {

        log.info("PUT /order/{}/status called with phase={}", guestOrderNumber, paymentPhase);

        GuestOrderDetailDTO order = guestService.getOrderDetail(guestOrderNumber);
        if (order == null) {
            log.warn("Order not found: {}", guestOrderNumber);
            return ResponseEntity.notFound().build();
        }

        guestService.updatePaymentStatus(order.getPurchaseId(), paymentPhase);
        log.info("Order status updated for purchaseId={}", order.getPurchaseId());
        return ResponseEntity.ok().build();
    }

//  판매 내역 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<MySaleListDTO>> getMySaleList(@RequestParam Long memberId) {
        log.info("GET /api/mypage/sale/list called with memberId={}", memberId);

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
            @RequestParam String paymentPhase) {

        log.info("PUT /api/mypage/sale/status/{} with paymentPhase={}", postId, paymentPhase);

        // TODO: service에 update 메서드 추가 필요
        // memberService.updateSaleStatus(postId, PaymentPhase.valueOf(paymentPhase));

        return ResponseEntity.ok().build();
    }



}