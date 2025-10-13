package com.example.crewstation.service.post;

import com.example.crewstation.domain.report.post.ReportPostVO;
import com.example.crewstation.domain.report.reply.ReportReplyVO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.dto.report.post.ReportPostDTO;
import com.example.crewstation.util.Criteria;

import java.util.List;

public interface PostService {
    //  신고하기
    public void report(ReportDTO reportDTO);

    public void reportReply(ReportDTO reportDTO);

//    관리자 다이어리 신고 목록
    public List<ReportPostDTO> getReportDiaries(int page);

//    관리자 다이어리 신고 상세
    public ReportPostDTO getReportDiaryDetail(Long reportId);

//    게시글 숨김
    public void hidePost(Long postId);

    default ReportPostVO toReportPostVO(ReportDTO reportDTO){
        return ReportPostVO.builder()
                .reportId(reportDTO.getId())
                .postId(reportDTO.getPostId()).build();
    }
    default ReportReplyVO toReportReplyVO(ReportDTO reportDTO){
        return ReportReplyVO.builder()
                .replyId(reportDTO.getReplyId())
                .reportId(reportDTO.getId()).build();
    }
}
