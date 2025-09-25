package com.example.crewstation.mapper.ask;

import com.example.crewstation.domain.ask.AskVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AskMapper {
    public void insert(AskVO askVO);
    public void update(AskVO askVO);
    public void delete(Long id);
}
