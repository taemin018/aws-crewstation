package com.example.crewstation.diary;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.DiaryCriteriaDTO;
import com.example.crewstation.dto.diary.DiaryDetailDTO;
import com.example.crewstation.dto.diary.LikedDiaryCriteriaDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.diary.DiaryService;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Member;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class ServiceTest {

    @Autowired
    private DiaryService diaryService;

    @Test
    public void testFindLikedDiaries() {
        Long memberId = 1L;
        ScrollCriteria criteria = new ScrollCriteria(2, 18);
        criteria.setSize(10);
        criteria.setOffset(0);

        LikedDiaryCriteriaDTO result = diaryService.getDiariesLikedByMemberId(memberId, criteria);

        List<LikedDiaryDTO> diaries = result.getLikedDiaryDTOs();

        log.info("조회 결과 건수 = {}", diaries.size());
        diaries.forEach(diary -> log.info("Diary: {}", diary));
    }

    @Test
    public void testCountLikedDiaries() {
        Long memberId = 1L;

        int count = diaryService.getCountDiariesLikedByMemberId(memberId);
        log.info("좋아요 일기 개수 = {}", count);
        assertThat(count).isGreaterThanOrEqualTo(0);
    }


    @Test
    public void testGetDiaries(){
        Search search = new Search();
        search.setPage(1);
        search.setKeyword("호주");
        search.setCategory("");
        search.setOrderType("");
        MemberDTO m = new MemberDTO();
        m.setId(1L);
        CustomUserDetails c = new CustomUserDetails(m);
        DiaryCriteriaDTO diaries = diaryService.getDiaries(search, c);
        log.info(diaries.toString());
    }

    @Test
    public void testGetDiary(){
        MemberDTO a =new MemberDTO();
        a.setId(2L);
        CustomUserDetails customUserDetails = new CustomUserDetails(a);
        DiaryDetailDTO diary = diaryService.getDiary(63L,customUserDetails);
        log.info(diary.toString());
    }
}
