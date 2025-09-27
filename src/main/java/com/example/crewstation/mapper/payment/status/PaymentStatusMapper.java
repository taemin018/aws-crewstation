package com.example.crewstation.mapper.payment.status;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentStatusMapper {
//    판매 요청 신청
    public void insert(Long purchaseId);
}
