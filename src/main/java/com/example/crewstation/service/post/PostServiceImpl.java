package com.example.crewstation.service.post;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.report.ReportDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostDAO postDAO;
    private final ReportDAO reportDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogReturnStatus
    public void report(ReportDTO reportDTO) {
        boolean isExist = postDAO.isActivePost(reportDTO.getPostId());
        log.info("{}",isExist);
        log.info("{}",reportDTO.toString());

        if(!isExist){
            throw new PostNotActiveException("이미 삭제된 상품입니다.");
        }
        reportDAO.saveReport(reportDTO);
    }


}
