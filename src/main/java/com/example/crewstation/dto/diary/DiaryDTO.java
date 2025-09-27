package com.example.crewstation.dto.diary;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class DiaryDTO {
    private Long postId;
    private String diarySecret;
    private String diaryLikeCount;
    private int diaryReplyCount;
    private String diaryCountryPathId;
    private String postTitle;
    private Long diaryId;
    private String filePath;
    private Long memberId;
}
