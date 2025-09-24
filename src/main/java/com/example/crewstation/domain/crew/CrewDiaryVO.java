package com.example.crewstation.domain.crew;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CrewDiaryVO {
    private Long id;
    private Long crewId;
    private Long diaryId;
}
