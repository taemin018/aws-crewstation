package com.example.crewstation.domain.diary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class DiaryVO {
    private String postId;
    private String diarySecret;
    private String diaryLikeContent;
    private int diaryReplyContent;
    private String diaryCountryPathId;
}
