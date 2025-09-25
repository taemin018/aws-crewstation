package com.example.crewstation.purchase;

import com.example.crewstation.mapper.purchase.PurchaseMapper;
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
    private PurchaseDAO purchaseDAO;

    @Test
    public void testFindAllByKeyWord() {
        Search search = new Search();
        search.setKeyword("호주");
        Criteria criteria = new Criteria(1,2);
        log.info("findAllByKeyWord {}", purchaseDAO.findAllByKeyWord(criteria, search));
    }
    @Test
    public void testFindCountAllByKeyWord() {
        Search search = new Search();
        search.setKeyword("호주");
        log.info("findAllByKeyWord {}", purchaseDAO.findCountAllByKeyWord(search));

    }
    @Test
    public void testFindByPostId(){
        log.info("testFindByPostId {}", purchaseDAO.findByPostId(3L));
    }
}
