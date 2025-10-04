package com.example.crewstation.mapper.payment;

import com.example.crewstation.dto.payment.PaymentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    public int insertPayment(PaymentDTO paymentDTO);
}
