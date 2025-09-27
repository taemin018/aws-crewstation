package com.example.crewstation.repository.file;

import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.mapper.file.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FileDAO {
    private final FileMapper fileMapper;
    //    회원가입 시 프로필
    public void save(FileVO FileVO) {
        fileMapper.insertFile(FileVO);
    }

}
