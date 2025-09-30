package com.example.crewstation.mapper.notice;

import com.example.crewstation.domain.notice.NoticeVO;
import com.example.crewstation.util.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    public List<NoticeVO> selectAllNotice(Criteria criteria);
    public int selectCountAll();
}
