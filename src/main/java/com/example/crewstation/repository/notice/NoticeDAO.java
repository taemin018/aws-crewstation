package com.example.crewstation.repository.notice;

import com.example.crewstation.domain.notice.NoticeVO;
import com.example.crewstation.mapper.notice.NoticeMapper;
import com.example.crewstation.util.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeDAO {

    private final NoticeMapper noticeMapper;

    //조회
    public List<NoticeVO> findAllNotice(Criteria criteria){

        return noticeMapper.selectAllNotice(criteria);
    }

    //전체 개수
    public int getTotal(){

        return noticeMapper.selectCountAll();
    }


}
