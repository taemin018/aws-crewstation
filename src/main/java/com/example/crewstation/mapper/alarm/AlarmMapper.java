package com.example.crewstation.mapper.alarm;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlarmMapper {

//    결제 알람 보내기
    public void insertPaymentAlarm(Long paymentStatusId);

//    좋아요 알람 보내기
    public void insertLikeAlarm(Long likeId);

//   좋아요 알람 삭제
    public void deleteLikeAlarm(Long likeId);

//   댓글 알람 추가
    public void insertReplyAlarm(Long postId);
}

