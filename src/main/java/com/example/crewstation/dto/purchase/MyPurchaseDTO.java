package com.example.crewstation.dto.purchase;


import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.dto.post.section.SectionDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="postId")
public class MyPurchaseDTO{
    private Long memberId;
    private Long postId;
    private Long fileId;
    private String postTitle;
    private String purchaseProductPrice;
    private int price;
    private int purchaseLimitTime;
    private int purchaseProductCount;
    private String purchaseCountry;
    private DeliveryMethod purchaseDeliveryMethod;
    private String filePath;
    private String fileName;
    private String memberName;
    private String socialImgUrl;
    private String limitDateTime;
    private int chemistryScore;
    private String createdDatetime;
    private String updatedDatetime;
    private String fileOriginName;
    private String address;
    private List<SectionDTO> sections;

    private String purchaseContent;
    private Long postSectionId;
    private Long thumbnail;
    private boolean prev;
    private Long prevMainImg;

}
