package com.example.crewstation.mapper.report;

import com.example.crewstation.dto.report.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {

    public void insertReport(ReportDTO reportDTO);
}
