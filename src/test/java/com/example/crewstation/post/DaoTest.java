package com.example.crewstation.post;

import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private PostDAO postDAO;

    @Test
    public void testSavePostReport(){
        postDAO.savePostReport(2L,1L);
    }

    @Test
    public void testIsActivePost(){
        log.info("testIsActivePost::::{}",postDAO.isActivePost(1L));
    }


}
