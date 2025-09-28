package com.example.crewstation.controller.payment;

import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.common.exception.SmsSendFailException;
import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/payment/**")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> requestPayment(@RequestBody PaymentStatusDTO paymentStatusDTO) {
        try{
            paymentService.requestPayment(paymentStatusDTO);
            return ResponseEntity.ok().body("판매요청 완료되었습니다.");
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (SmsSendFailException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
