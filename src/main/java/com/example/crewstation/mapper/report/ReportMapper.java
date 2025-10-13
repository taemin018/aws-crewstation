package com.example.crewstation.mapper.report;

import com.example.crewstation.domain.report.ReportVO;
import com.example.crewstation.domain.report.post.ReportPostVO;
import com.example.crewstation.domain.report.reply.ReportReplyVO;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.dto.report.post.ReportPostDTO;
import com.example.crewstation.util.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
//  신고하기 추가
    public void insertReport(ReportDTO reportDTO);
//  신고하기 연결부분 추가
    public void insertReportPost(ReportPostVO reportPostVO);

    public void insertReportReply(ReportReplyVO reportReplyVO);

//    관리자 다이어리 신고 내역
    public List<ReportPostDTO> selectAllReportDiaries(Criteria criteria);

//    관리자 다이어리 신고 갯수
    public int selectReportDiariesCount();
}
