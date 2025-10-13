package com.example.crewstation.service.post;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.aop.aspect.annotation.LogStatus;
import com.example.crewstation.common.enumeration.Status;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.dto.report.post.ReportPostDTO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.report.ReportDAO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostDAO postDAO;
    private final ReportDAO reportDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogStatus
    public void report(ReportDTO reportDTO) {
        boolean isExist = postDAO.isActivePost(reportDTO.getPostId());
        log.info("{}",isExist);
        log.info("{}",reportDTO.toString());

        if(!isExist){
            throw new PostNotActiveException("이미 삭제된 상품입니다.");
        }
        reportDAO.saveReport(reportDTO);

        reportDAO.saveReportPost(toReportPostVO(reportDTO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogStatus
    public void reportReply(ReportDTO reportDTO) {
        boolean isExist = postDAO.isActivePost(reportDTO.getPostId());
        log.info("{}",isExist);
        log.info("{}",reportDTO.toString());

        if(!isExist){
            throw new PostNotActiveException("이미 삭제된 다이어리입니다.");
        }
        reportDAO.saveReport(reportDTO);
        reportDAO.saveReportReply(toReportReplyVO(reportDTO));
    }

    @Override
    public List<ReportPostDTO> getReportDiaries(int page) {
        ScrollCriteria scrollCriteria = new ScrollCriteria(page, 10);
//        log.info("스크롤 페이지 번호 = {}", scrollCriteria.getPage());
        return reportDAO.findAllReportDiaries(scrollCriteria);
    }

    @Override
    public ReportPostDTO getReportDiaryDetail(Long reportId) {
        return reportDAO.findReportDiaryDetail(reportId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void hidePost(Long postId) {
        log.info("게시글 숨김 postId={}", postId);
        reportDAO.updatePostStatus(postId, Status.INACTIVE.getValue());
    }


}
