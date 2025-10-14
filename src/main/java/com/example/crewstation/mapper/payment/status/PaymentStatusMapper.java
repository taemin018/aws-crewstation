package com.example.crewstation.mapper.payment.status;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.dto.payment.status.PaymentCriteriaDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.util.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PaymentStatusMapper {
//    판매 요청 신청
    public void insert(PaymentStatusDTO paymentStatusDTO);

//  구매 내역 조회
    public  PaymentStatusDTO selectByPurchaseId(Long purchaseId);

//  결제 상태 업데이트
    public void updatePaymentStatus(@Param("purchaseId") Long purchaseId, @Param("paymentPhase") PaymentPhase paymentPhase);

    //    관리자 결제 내역
    public List<PaymentCriteriaDTO> selectPayment(Criteria criteria);

//    결제 갯수
    public int countPayment();

}
