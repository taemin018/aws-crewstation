package com.example.crewstation.repository.report;

import com.example.crewstation.domain.report.ReportVO;
import com.example.crewstation.domain.report.post.ReportPostVO;
import com.example.crewstation.domain.report.reply.ReportReplyVO;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.dto.report.post.ReportPostDTO;
import com.example.crewstation.mapper.report.ReportMapper;
import com.example.crewstation.util.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

//    관리자 다이어리 신고 내역
    public List<ReportPostDTO> findAllReportDiaries(Criteria criteria) {
        return reportMapper.selectAllReportDiaries(criteria);
    }

//    관리자 다이어리 신고 갯수
    public int countAllReportDiaries() {
        return reportMapper.selectReportDiariesCount();
    }
}
