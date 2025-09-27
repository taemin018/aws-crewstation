package com.example.crewstation.dto.diary;


import com.example.crewstation.util.Criteria;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LikedDiaryCriteriaDTO {
    private List<LikedDiaryDTO> likedDiaryDTOs;
    private Criteria criteria;
}
