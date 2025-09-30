package com.example.crewstation.repository.purchase;

import com.example.crewstation.domain.purchase.PurchaseVO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.mapper.purchase.PurchaseMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PurchaseDAO {
    private final PurchaseMapper purchaseMapper;

    //  검색결과에 따른 기프트 목록 보여주기
    public List<PurchaseDTO> findAllByKeyWord(Criteria criteria,Search search) {
        return purchaseMapper.selectAllByKeyWord(criteria, search);
    }
    //  검색 결과에 따른 기프트 수 보여주기
    public int findCountAllByKeyWord(Search search) {
        return purchaseMapper.selectCountAllByKeyWord(search);
    }
    //  기프트 상세 보기
    public Optional<PurchaseDetailDTO> findByPostId(Long postId){
        return purchaseMapper.selectByPostId(postId);
    }
//  조회 수 증가
    public void increaseReadCount(Long postId) {
        purchaseMapper.updateReadCount(postId);
    }
//  기프트 추가
    public void save(PurchaseVO purchaseVO) {
        purchaseMapper.insert(purchaseVO);
    }

}
