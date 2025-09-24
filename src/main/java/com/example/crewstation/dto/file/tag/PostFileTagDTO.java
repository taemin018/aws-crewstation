package com.example.crewstation.dto.file.tag;

import com.example.crewstation.common.enumeration.Status;
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
public class PostFileTagDTO {
    private Long id;
    private float tagLeft;
    private float tagRight;
    private Long memberId;
    private Long postSectionFileId;
    private String createdDatetime;
    private String updatedDatetime;
}
