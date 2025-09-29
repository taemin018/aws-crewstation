//package com.example.crewstation.diary;
//
//import com.example.crewstation.dto.diary.LikedDiaryDTO;
//import com.example.crewstation.repository.diary.DiaryDAO;
//import com.example.crewstation.service.diary.DiaryService;
//import com.example.crewstation.service.purchase.PurchaseService;
//import com.example.crewstation.util.Criteria;
//import com.example.crewstation.util.Search;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@Slf4j
//public class ServiceTest {
//
//    @Autowired
//    private DiaryService diaryService;
//
//    @Test
//    public void testFindLikedDiaries() {
//        Long memberId = 1L;
//        Criteria criteria = new Criteria(2,18);
//        criteria.setRowCount(10);
//        criteria.setOffset(0);
//
//        List<LikedDiaryDTO> diaries = diaryService.getDiariesLikedByMemberId(memberId, criteria);
//        log.info("조회 결과 건수 = {}", diaries.size());
//        assertThat(diaries).isNotNull();
//    }
//
//    @Test
//    public void testCountLikedDiaries() {
//        Long memberId = 1L;
//
//        int count = diaryService.getCountDiariesLikedByMemberId(memberId);
//        log.info("좋아요 일기 개수 = {}", count);
//        assertThat(count).isGreaterThanOrEqualTo(0);
//    }
//}
