package com.example.crewstation.mapper.reply;

import com.example.crewstation.dto.reply.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    public List<ReplyDTO> selectAllByPostId(Long postId);
}
