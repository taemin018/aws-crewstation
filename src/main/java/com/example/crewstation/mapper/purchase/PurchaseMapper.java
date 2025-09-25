package com.example.crewstation.mapper.purchase;

import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PurchaseMapper {

//  검색결과에 따른 기프트 목록 보여주기
    public List<PurchaseDTO> selectAllByKeyWord(@Param("criteria") Criteria criteria, @Param("search") Search search);
//  검색 결과에 따른 기프트 수 보여주기
    public int selectCountAllByKeyWord(@Param("search") Search search);

//  기프트 상세 보기
    public Optional<PurchaseDetailDTO> selectByPostId(Long postId);
}
