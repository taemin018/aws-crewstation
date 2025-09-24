package com.example.crewstation.repository.purchase;

import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.mapper.purchase.PurchaseMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
