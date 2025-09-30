package com.example.crewstation.mapper.file;

import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.dto.file.FileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
//    insert
    public void insertFile(FileVO fileVO);

//  파일 추가
    public void insert(FileDTO fileDTO);

//  파일 삭제
    public void delete(Long id);
}
