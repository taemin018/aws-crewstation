package com.example.crewstation.mapper.section;

import com.example.crewstation.dto.post.section.SectionDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SectionMapper {
//  상품글 섹션에 내용과 이미지 가져오기
    public List<SectionDTO> selectSectionsByPostId(Long postId);

//  섹션 작성하기
    public void insert(PurchaseDTO purchaseDTO);
}
