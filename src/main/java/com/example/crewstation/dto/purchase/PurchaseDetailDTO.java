package com.example.crewstation.dto.purchase;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.dto.post.section.SectionDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ToString
public class PurchaseDetailDTO {
    private Long id;
    private Long postId;
    private String postTitle;
    private int postReadCount;
    private int purchaseProductPrice;
    private int purchaseLimitTime;
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
    private String fileOriginName;
    private String address;
    private List<SectionDTO> sections;
}
