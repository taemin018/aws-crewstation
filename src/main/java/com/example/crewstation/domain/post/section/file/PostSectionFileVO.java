package com.example.crewstation.domain.post.section.file;


import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.Type;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "fileId")
public class PostSectionFileVO extends Period {
    private Long fileId;
    private Long postSectionId;
    private Type imageType;
}
