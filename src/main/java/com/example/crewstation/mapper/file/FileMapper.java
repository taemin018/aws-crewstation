package com.example.crewstation.mapper.file;

import com.example.crewstation.domain.file.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
//    insert
    public void insertFile(FileVO fileVO);
}
