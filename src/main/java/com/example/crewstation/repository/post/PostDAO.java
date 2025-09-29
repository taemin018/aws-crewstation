package com.example.crewstation.repository.post;

import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.mapper.post.PostMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostDAO {
    private final PostMapper postMapper;

    //    게시글 신고 추가해주기
    public void savePostReport(Long reportId, Long postId) {
        postMapper.insertPostReport(reportId, postId);
    }

    //    게시글 존재 여부 확인
    public boolean isActivePost(Long postId) {
        return postMapper.existsActivePost(postId);

    }

//    게시글 작성
    public void savePost(PurchaseDTO purchaseDTO) {
        postMapper.insert(purchaseDTO);
    }
}
