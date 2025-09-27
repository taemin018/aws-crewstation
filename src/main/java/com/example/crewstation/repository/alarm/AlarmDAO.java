package com.example.crewstation.repository.alarm;

import com.example.crewstation.mapper.alarm.AlarmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AlarmDAO {

    private final AlarmMapper alarmMapper;

    //    결제 알람 보내기
    public void save(Long paymentStatusId){
        alarmMapper.insert(paymentStatusId);
    }
}
