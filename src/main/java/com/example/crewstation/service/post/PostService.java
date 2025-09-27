package com.example.crewstation.service.post;

import com.example.crewstation.dto.report.ReportDTO;

public interface PostService {
    //  신고하기
    public void report(ReportDTO reportDTO);
}
