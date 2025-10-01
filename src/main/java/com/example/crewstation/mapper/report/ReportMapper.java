package com.example.crewstation.mapper.report;

import com.example.crewstation.domain.report.ReportVO;
import com.example.crewstation.domain.report.post.ReportPostVO;
import com.example.crewstation.dto.report.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {
//  신고하기 추가
    public void insertReport(ReportDTO reportDTO);
//  신고하기 연결부분 추가
    public void insertReportPost(ReportPostVO reportPostVO);
}
