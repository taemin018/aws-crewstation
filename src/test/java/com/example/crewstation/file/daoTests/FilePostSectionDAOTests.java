package com.example.crewstation.file.daoTests;

import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.domain.file.section.PostSectionFileVO;
import com.example.crewstation.repository.file.section.FilePostSectionDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class FilePostSectionDAOTests {
    @Autowired
    private FilePostSectionDAO filePostSectionDAO;

    @Test
    @Transactional
    public void saveFileTest() {
        PostSectionFileVO postSectionFileVO = PostSectionFileVO.builder()
                .fileId(20L)
                .postSectionId(20L)
                .imageType(Type.MAIN)
                .build();
        filePostSectionDAO.save(postSectionFileVO);
    }
}
