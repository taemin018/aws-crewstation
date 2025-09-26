package com.example.crewstation.service.purchase;

import com.example.crewstation.dto.purchase.PurchaseCriteriaDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.util.Search;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
// 무한 스크롤 기프트 목록들 가져오기
    public PurchaseCriteriaDTO getPurchases(Search search);

// 기프트 상품 상세 내용 가져오기
    public Optional<PurchaseDetailDTO> getPurchase(Long postId);

//  신고하기
    public void report(ReportDTO reportDTO);
}
