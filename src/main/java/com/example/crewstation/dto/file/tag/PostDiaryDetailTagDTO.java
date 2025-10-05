package com.example.crewstation.dto.file.tag;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ToString
public class PostDiaryDetailTagDTO {
    private Long memberId;
    private Long crewId;
    private String[] countries;
    private String postTitle;
    private boolean secret;
    private Long postId;
    private Long diaryPathId;
    private List<ImageDTO> images;
    private List<Long> countryIds;
    private Long postSectionId;
    private Long fileId;
}
