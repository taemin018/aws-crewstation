package com.example.crewstation.mapper.alarm;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlarmMapper {

//    결제 알람 보내기
    public void insert(Long paymentStatusId);
}

