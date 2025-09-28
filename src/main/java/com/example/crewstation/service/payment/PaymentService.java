package com.example.crewstation.service.payment;

import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;

public interface PaymentService {
    public void requestPayment(PaymentStatusDTO paymentStatusDTO);

    default GuestVO toVO(PaymentStatusDTO  paymentStatusDTO) {
        return GuestVO.builder()
                .memberId(paymentStatusDTO.getMemberId())
                .guestPhone(paymentStatusDTO.getMemberPhone())
                .guestOrderNumber(paymentStatusDTO.getGuestOrderNumber())
                .addressDetail(paymentStatusDTO.getAddressDetail())
                .address(paymentStatusDTO.getAddress())
                .addressZipCode(paymentStatusDTO.getAddressZipCode())
                .build();
    }
}
