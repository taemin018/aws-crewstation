package com.example.crewstation.dto.guest;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class GuestOrderDetailDTO {
    // 주문(게스트) 정보
    private Long id;
    private String guestName;
    private String guestPhone;
    private String addressZipCode;
    private String guestAddress;
    private String guestAddressDetail;
    private String guestOrderNumber;

    // 결제
    private String paymentStatus;

    // 구매(상품)
    private Long postId;
    private String postTitle;
    private int purchaseProductPrice;
    private String purchaseCountry;
    private DeliveryMethod purchaseDeliveryMethod;
    private String mainImage;

    // 판매자
    private Long memberId;
    private String sellerName;
    private String sellerPhone;
    private String createdDatetime;
    private String updatedDatetime;
}
