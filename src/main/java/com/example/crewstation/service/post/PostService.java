package com.example.crewstation.service.post;

import com.example.crewstation.domain.report.post.ReportPostVO;
import com.example.crewstation.domain.report.reply.ReportReplyVO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.report.ReportDTO;

public interface PostService {
    //  신고하기
    public void report(ReportDTO reportDTO);

    public void reportReply(ReportDTO reportDTO);

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
