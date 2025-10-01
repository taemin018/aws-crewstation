package com.example.crewstation.mapper.payment.status;

import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentStatusMapper {
//    판매 요청 신청
    public void insert(PaymentStatusDTO paymentStatusDTO);

//  구매 내역 조회
    public  PaymentStatusDTO selectByPurchaseId(Long purchaseId);
}
