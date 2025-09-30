package com.example.crewstation.controller.payment;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.common.exception.SmsSendFailException;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/payment/**")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> requestPayment(@RequestBody PaymentStatusDTO paymentStatusDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try{
            if(userDetails != null){
                paymentStatusDTO.setMemberId(userDetails.getId());
            }
//            paymentStatusDTO.setMemberId(1L);
            Map<String, Object> message = paymentService.requestPayment(paymentStatusDTO);
            return ResponseEntity.ok().body(message);
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (SmsSendFailException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
