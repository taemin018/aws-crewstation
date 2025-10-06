package com.example.crewstation.service.alarm;

import com.example.crewstation.repository.alarm.AlarmDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService {
    private final AlarmDAO alarmDAO;

    @Override
    public int getUnreadCount(Long memberId) {
        return alarmDAO.selectUnreadCount(memberId);
    }
}
