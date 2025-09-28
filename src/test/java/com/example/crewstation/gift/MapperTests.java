package com.example.crewstation.gift;

import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.mapper.gift.GiftMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MapperTests {
    @Autowired
    private GiftMapper giftMapper;
    @Autowired
    private GiftDTO giftDTO;

    @Test
    public void giftTest() {
        List<GiftDTO> gifts = giftMapper.getGift(5);
        gifts.forEach(giftDTO -> log.info("gift = {}", giftDTO));
    }
}
