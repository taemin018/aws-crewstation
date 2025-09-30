package com.example.crewstation.mapper.post;

import com.example.crewstation.domain.post.PostVO;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
//    게시글 신고 추가해주기
    public void insertPostReport(@Param("reportId")Long reportId ,@Param("postId") Long postId);
//    게시글 존재 여부 확인
    public boolean existsActivePost(Long postId);

//    게시글 작성
    public void insert(PurchaseDTO purchaseDTO);
//    게시글 수정
    public void update(PostVO postVO);

//   게시글 삭제 소프트 딜리트
    public void updatePostStatus(Long id);
}
