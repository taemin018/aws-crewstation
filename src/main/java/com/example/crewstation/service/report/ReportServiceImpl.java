package com.example.crewstation.service.report;

import com.example.crewstation.common.enumeration.ProcessStatus;
import com.example.crewstation.repository.report.ReportDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resolveReport(Long reportId) {
        log.info("신고 처리 상태 변경 reportId={}", reportId);
        reportDAO.updateReportProcessStatus(reportId, ProcessStatus.RESOLVED.getValue());
    }
}
