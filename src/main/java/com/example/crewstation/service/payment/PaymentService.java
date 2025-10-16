package com.example.crewstation.service.payment;

import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;

import java.util.Map;

public interface PaymentService {
    public Map<String, Object> requestPayment(PaymentStatusDTO paymentStatusDTO);

    public void completePayment(Long purchaseId, PaymentDTO paymentDTO);

    public void selectPayment(int page);

    default GuestVO toVO(PaymentStatusDTO  paymentStatusDTO) {
        return GuestVO.builder()
                .memberId(paymentStatusDTO.getMemberId())
                .guestPhone(paymentStatusDTO.getMemberPhone())
                .guestPassword(paymentStatusDTO.getGuestPassword())
                .guestOrderNumber(paymentStatusDTO.getGuestOrderNumber())
                .addressDetail(paymentStatusDTO.getAddressDetail())
                .address(paymentStatusDTO.getAddress())
                .addressZipCode(paymentStatusDTO.getAddressZipCode())
                .build();
    }

}
