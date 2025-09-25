package com.example.crewstation.dto.diary;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class DiaryDTO {
    private String postId;
    private String diarySecret;
    private String diaryLikeContent;
    private int diaryReplyContent;
    private String diaryCountryPathId;
}
