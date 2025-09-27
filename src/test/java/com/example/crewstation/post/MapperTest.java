package com.example.crewstation.post;

import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.mapper.post.PostMapper;
import com.example.crewstation.mapper.purchase.PurchaseMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private PostMapper postMapper;

    @Test
    public void testInsertPostReport() {
        Long reportId = 1L;
        Long postId = 1L;
        postMapper.insertPostReport(reportId,postId);
    }

    @Test
    public void testExistsActivePost() {
        log.info("exists active post {}",postMapper.existsActivePost(1L));
    }
}
