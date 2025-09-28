package com.example.crewstation.repository.payment.status;


import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.mapper.payment.status.PaymentStatusMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PaymentStatusDAO {
    private final PaymentStatusMapper paymentStatusMapper;

    //    판매 요청 신청
    public void save(PaymentStatusDTO paymentStatusDTO) {
        paymentStatusMapper.insert(paymentStatusDTO);
    }
}
