package com.example.crewstation.controller.guest;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.diary.LikedDiaryCriteriaDTO;
import com.example.crewstation.dto.diary.ReplyDiaryCriteriaDTO;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
import com.example.crewstation.service.diary.DiaryService;
import com.example.crewstation.service.guest.GuestService;
import com.example.crewstation.util.ScrollCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/guest")
public class GuestRestController {

    private final GuestService guestService;

    // 주문번호로 단건 조회
    @GetMapping("/order-detail/{guestOrderNumber}")
    public ResponseEntity<GuestOrderDetailDTO> getOrderDetail(@PathVariable String guestOrderNumber) {
        GuestOrderDetailDTO orderDetail = guestService.getOrderDetail(guestOrderNumber);
        return ResponseEntity.ok(orderDetail);
    }

    // 결제 상태 업데이트
    @PutMapping("/order/{guestOrderNumber}/status")
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



}