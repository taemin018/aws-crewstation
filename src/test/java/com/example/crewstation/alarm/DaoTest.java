package com.example.crewstation.alarm;

import com.example.crewstation.mapper.alarm.AlarmMapper;
import com.example.crewstation.repository.alarm.AlarmDAO;
import com.example.crewstation.repository.post.PostDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private AlarmDAO alarmDAO;

    @Test
    @Transactional
    public void testInsert() {
        alarmDAO.save(2L);
    }

}
