package com.example.crewstation.dto.purchase;


import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.DeliveryMethod;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="postId")
public class PurchaseDTO{
    private Long postId;
    private String postTitle;
    private String purchaseProductPrice;
    private int purchaseLimitTime;
    private String purchaseContent;
    private Long memberId;
    private int thumbnail;
    private int purchaseProductCount;
    private String purchaseCountry;
    private DeliveryMethod purchaseDeliveryMethod;
    private String filePath;
    private String fileName;
    private String memberName;
    private String limitDateTime;
    private int chemistryScore;
    private String createdDatetime;
    private String updatedDatetime;
}
