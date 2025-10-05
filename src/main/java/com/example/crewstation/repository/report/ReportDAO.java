package com.example.crewstation.repository.report;

import com.example.crewstation.domain.report.ReportVO;
import com.example.crewstation.domain.report.post.ReportPostVO;
import com.example.crewstation.domain.report.reply.ReportReplyVO;
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

    //  신고하기 연결부분 추가
    public void saveReportPost(ReportPostVO reportPostVO){
        reportMapper.insertReportPost(reportPostVO);
    }
    public void saveReportReply(ReportReplyVO reportReplyVO){
        reportMapper.insertReportReply(reportReplyVO);
    }
}
