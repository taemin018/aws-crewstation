package com.example.crewstation.dto.file.section;


import com.example.crewstation.common.enumeration.Type;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="fileId")
public class PostSectionFileDTO {
    private Long fileId;
    private Long postSectionId;
    private Type imageType;
    private String createDatetime;
    private String updateDatetime;
}
