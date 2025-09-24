package com.example.crewstation.domain.file.tag;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class PostFileTagVO extends Period {
    private Long id;
    private String postContent;
    private Long postId;
}
