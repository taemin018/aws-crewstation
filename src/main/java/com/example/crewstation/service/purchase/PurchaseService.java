package com.example.crewstation.service.purchase;

import com.example.crewstation.dto.purchase.PurchaseCriteriaDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.util.Search;

import java.util.List;

public interface PurchaseService {
// 무한 스크롤 기프트 목록들 가져오기
    public PurchaseCriteriaDTO getPurchases(Search search);
}
