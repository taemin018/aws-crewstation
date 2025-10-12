package com.example.crewstation.dto.member;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MySaleListDTO {
    private Long id;
    private String paymentStatus;

    private Long postId;
    private Long purchaseId;
    private String postTitle;
    private int purchaseProductPrice;
    private int purchaseProductCount;
    private String mainImage;

    private String createdDatetime;
    private String updatedDatetime;
}
