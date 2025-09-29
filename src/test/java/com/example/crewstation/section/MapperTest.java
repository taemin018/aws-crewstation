package com.example.crewstation.section;

import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.mapper.section.SectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    SectionMapper sectionMapper;

    @Test
    public void testSelectSectionsByPostId(){
        log.info("selectSectionsByPostId {}", sectionMapper.selectSectionsByPostId(3L));
    }

    @Test
    @Transactional
    public void testInsert(){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPurchaseContent("test");
        purchaseDTO.setPostId(14L);
        sectionMapper.insert(purchaseDTO);
        log.info("purchaseDTO postSectionId:{}", purchaseDTO.getPostSectionId());
    }
}
