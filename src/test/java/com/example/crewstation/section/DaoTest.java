package com.example.crewstation.section;

import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.section.SectionDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private SectionDAO sectionDAO;

    @Test
    public void testFindSectionsByPostId(){
        log.info("findSectionsByPostId {}",sectionDAO.findSectionsByPostId(2L));
    }

    @Test
    @Transactional
    public void testSave(){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPurchaseContent("test");
        purchaseDTO.setPostId(14L);
        sectionDAO.save(purchaseDTO);
        log.info("purchaseDTO postSectionId:{}", purchaseDTO.getPostSectionId());
    }
}
