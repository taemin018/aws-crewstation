package com.example.crewstation.controller.payment;

import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/**")
@Slf4j
@RequiredArgsConstructor
public class PaymentRestController {
    private final PaymentService paymentService;

    @PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestBody PaymentDTO paymentDTO) {
        paymentService.completePayment(paymentDTO.getPurchaseId(), paymentDTO);
        return ResponseEntity.ok("결제가 완료되었습니다.");
    }
}
