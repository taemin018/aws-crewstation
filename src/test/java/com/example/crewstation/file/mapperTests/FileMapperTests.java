package com.example.crewstation.file.mapperTests;

import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.mapper.file.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FileMapperTests {
    @Autowired
    private FileMapper fileMapper;

    @Test
    public void testInsertFile() {
        FileVO fileVO = FileVO.builder()
                .fileSize("12MB")
                .filePath("../../test")
                .fileOriginName("test")
                .fileName("test")
                .build();
        fileMapper.insertFile(fileVO);
    }
}
