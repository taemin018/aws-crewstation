package com.example.crewstation.file.mapperTests;

import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.domain.file.section.PostSectionFileVO;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.mapper.file.FileMapper;
import com.example.crewstation.mapper.file.post.section.FilePostSectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class FilePostSectionMapperTests {
    @Autowired
    private FilePostSectionMapper filePostSectionMapper;


    @Test
    @Transactional
    public void saveFileTest() {
        PostSectionFileVO postSectionFileVO = PostSectionFileVO.builder()
                .fileId(20L)
                .postSectionId(20L)
                .imageType(Type.MAIN)
                .build();
        filePostSectionMapper.insert(postSectionFileVO);
    }


}
