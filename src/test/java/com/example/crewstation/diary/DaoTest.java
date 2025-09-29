//package com.example.crewstation.diary;
//
//import com.example.crewstation.dto.diary.LikedDiaryDTO;
//import com.example.crewstation.repository.diary.DiaryDAO;
//import com.example.crewstation.repository.purchase.PurchaseDAO;
//import com.example.crewstation.util.Criteria;
//import com.example.crewstation.util.Search;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//
//@SpringBootTest
//@Slf4j
//public class DaoTest {
//    @Autowired
//    private DiaryDAO diaryDAO;
//
//    @Test
//    public void testFindLikedDiaries() {
//        Long memberId = 1L;
//        Criteria criteria = new Criteria(1,17);
//        criteria.setRowCount(3);
//        criteria.setOffset(0);
//
//        List<LikedDiaryDTO> diaries = diaryDAO.findDiariesLikedByMemberId(memberId, criteria);
//
//        log.info("FindLikedDiaries size = {}", diaries.size());
//        diaries.forEach(diary -> log.info("Diary: {}", diary));
//    }
//
//    @Test
//    public void testCountLikedDiaries() {
//        Long memberId = 1L;
//        int count = diaryDAO.countDiariesLikedByMemberId(memberId);
//    }
//}
