package com.example.crewstation.repository.alarm;

import com.example.crewstation.mapper.alarm.AlarmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AlarmDAO {

    private final AlarmMapper alarmMapper;

    //    결제 알람 보내기
    public void savePaymentAlarm(Long paymentStatusId) {
        alarmMapper.insertPaymentAlarm(paymentStatusId);
    }

    //    좋아요 알람 보내기
    public void saveLikeAlarm(Long likeId) {
        alarmMapper.insertLikeAlarm(likeId);
    }

    //   좋아요 알람 삭제
    public void deleteLikeAlarm(Long likeId){
        alarmMapper.deleteLikeAlarm(likeId);
    }

    //   댓글 알람 추가
    public void saveReplyAlarm(Long postId){
        alarmMapper.insertReplyAlarm(postId);
    }

//    안읽은 알람 갯수
     public int selectUnreadCount(Long memberId){
        return alarmMapper.selectUnreadCount(memberId);
     }
}
