package com.example.crewstation.repository.report;

import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.mapper.report.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportDAO {
    private final ReportMapper reportMapper;

    //  신고하기 추가
    public void saveReport(ReportDTO reportDTO){
        reportMapper.insertReport(reportDTO);
    };
}
