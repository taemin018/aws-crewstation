package com.example.crewstation.domain.diary;


import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.common.enumeration.Secret;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class DiaryVO extends Period {
    private Long postId;
    private Secret diarySecret;
    private int diaryLikeCount;
    private int diaryReplyCount;
    private Long countryPateId;
}
