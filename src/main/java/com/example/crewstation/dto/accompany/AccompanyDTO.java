package com.example.crewstation.dto.accompany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class AccompanyDTO {
    private Long postId;
    private String postTitle;
    private String accompanyStatus;
    private String startDate;
    private String endDate;
    private String countries;
    private String filePath;
    private String fileName;
    private String crewName;
    private String memberName;
    private String socialImgUrl;
}
