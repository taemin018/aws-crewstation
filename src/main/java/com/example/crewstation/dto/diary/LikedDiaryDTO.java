package com.example.crewstation.dto.diary;


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
@EqualsAndHashCode(of="id")
public class LikedDiaryDTO {
    private Long postId;
    private String postTitle;
    private String filePath;
    private String fileName;
    private String memberName;
    private String createdDatetime;
    private String updatedDatetime;
}
