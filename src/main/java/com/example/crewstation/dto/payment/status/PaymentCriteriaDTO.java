package com.example.crewstation.dto.payment.status;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.util.Criteria;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PaymentCriteriaDTO {
    private List<PaymentStatusDTO> paymentList;
    private Criteria criteria;
    private Long id;
    private Long paymentId;
    private Long purchaseId;
    private Long memberId;
    private boolean guest;
    private String memberPhone;
    private String addressZipCode;
    private String addressDetail;
    private String address;
    private String guestOrderNumber;
    private PaymentPhase paymentPhase;
    private String createdDatetime;
    private String updatedDatetime;
    private String receiptId;
    private Integer amount;
}
