package com.example.crewstation.post;

import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.service.post.PostService;
import com.example.crewstation.service.purchase.PurchaseService;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ServiceTest {

    @Autowired
    private PostService postService;

    @Test
    public void testReport() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setMemberId(1L);
        reportDTO.setReportContent("불법");
        reportDTO.setPostId(1L);
        postService.report(reportDTO);
    }
}
