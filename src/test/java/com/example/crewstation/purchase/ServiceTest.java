package com.example.crewstation.purchase;

import com.example.crewstation.service.purchase.PurchaseService;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ServiceTest {
    @Autowired
    private PurchaseService purchaseService;

    @Test
    public void testGetPurchases(){
        Search search = new Search();
        search.setPage(2);
        search.setKeyword("test");
//        log.info("{}",);
        Criteria criteria = new Criteria(search.getPage(),1);
        log.info("{}",criteria.toString());
        log.info("testGetPurchases {}", purchaseService.getPurchases(search));
    }

    @Test
    public void testGetPurchase(){
        purchaseService.getPurchase(3L);
    }
}
