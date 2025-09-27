package com.example.crewstation.dto.payment.status;

import com.example.crewstation.common.enumeration.PaymentPhase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id",callSuper = false)
public class PaymentStatusDTO {
    private Long id;
    private Long paymentId;
    private Long purchaseId;
    private PaymentPhase paymentPhase;
    private String createdDatetime;
    private String updatedDatetime;
}
