package com.example.crewstation.payment.status;

import com.example.crewstation.mapper.payment.status.PaymentStatusMapper;
import com.example.crewstation.mapper.post.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class MapperTest {

    @Autowired
    private PaymentStatusMapper paymentStatusMapper;

    @Test
//    @Transactional
    public void testInsert() {
        paymentStatusMapper.insert(1L,1L);
    }
}
