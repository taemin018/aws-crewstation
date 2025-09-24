package com.example.crewstation.purchase;

import com.example.crewstation.mapper.purchase.PurchaseMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private PurchaseMapper purchaseMapper;

    @Test
    public void testSelectAllByKeyWord() {
        Search search = new Search();
        search.setKeyword("호주");
        Criteria criteria = new Criteria(1, 10);

        log.info("testSelectAllByKeyWord {}", purchaseMapper.selectAllByKeyWord(criteria, search));
    }

    @Test
    public void testSelectCountAllByKeyWord() {
        Search search = new Search();
        search.setKeyword("호주");

        log.info("testSelectAllByKeyWord {}", purchaseMapper.selectCountAllByKeyWord(search));
    }
}
