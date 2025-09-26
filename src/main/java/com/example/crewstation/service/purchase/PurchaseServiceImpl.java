package com.example.crewstation.service.purchase;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.common.exception.PurchaseNotFoundException;
import com.example.crewstation.dto.post.section.SectionDTO;
import com.example.crewstation.dto.purchase.PurchaseCriteriaDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.repository.report.ReportDAO;
import com.example.crewstation.repository.section.SectionDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.DateUtils;
import com.example.crewstation.util.PriceUtils;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseDAO purchaseDAO;
    private final S3Service s3Service;
    private final SectionDAO sectionDAO;
    private final PostDAO postDAO;
    private final ReportDAO reportDAO;

    @Override
    @LogReturnStatus
    public PurchaseCriteriaDTO getPurchases(Search search) {
        PurchaseCriteriaDTO purchaseCriteriaDTO = new PurchaseCriteriaDTO();
        int page = search.getPage();
        Criteria criteria = new Criteria(page, purchaseDAO.findCountAllByKeyWord(search));
        List<PurchaseDTO> purchases = purchaseDAO.findAllByKeyWord(criteria, search);
        purchases.forEach(purchase -> {
            purchase.setPurchaseProductPrice(PriceUtils.formatMoney(purchase.getPurchaseProductPrice()));
            purchase.setFilePath(s3Service.getPreSignedUrl(purchase.getFilePath(), Duration.ofMinutes(5)));
            purchase.setLimitDateTime(DateUtils.calcLimitDateTime(purchase.getCreatedDatetime(), purchase.getPurchaseLimitTime()));
        });
        criteria.setHasMore(purchases.size() > criteria.getRowCount());
        if(criteria.isHasMore()){
            purchases.remove(purchases.size() - 1);
        }
        purchaseCriteriaDTO.setPurchaseDTOs(purchases);
        purchaseCriteriaDTO.setCriteria(criteria);
        purchaseCriteriaDTO.setSearch(search);
        return purchaseCriteriaDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "purchases", key="'purchases_' + #postId")
    @LogReturnStatus
    public Optional<PurchaseDetailDTO> getPurchase(Long postId) {
        purchaseDAO.increaseReadCount(postId);
        Optional<PurchaseDetailDTO> purchaseDetail = purchaseDAO.findByPostId(postId);
        List<SectionDTO> sections = sectionDAO.findSectionsByPostId(postId);
        sections.forEach(section -> {
            section.setFilePath(s3Service.getPreSignedUrl(section.getFilePath(), Duration.ofMinutes(5)));
        });
        purchaseDetail.ifPresent(purchase -> {
            purchase.setPurchaseProductPrice(PriceUtils.formatMoney(purchase.getPurchaseProductPrice()));
            purchase.setFilePath(s3Service.getPreSignedUrl(purchase.getFilePath(), Duration.ofMinutes(5)));
            purchase.setLimitDateTime(DateUtils.calcLimitDateTime(purchase.getCreatedDatetime(), purchase.getPurchaseLimitTime()));
            purchase.setSections(sections);
        });
        return purchaseDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogReturnStatus
    public void report(ReportDTO reportDTO) {
        boolean isExist = postDAO.isActivePost(reportDTO.getPostId());
        if(!isExist){
            throw new PostNotActiveException("이미 삭제된 상품입니다.");
        }
        reportDAO.saveReport(reportDTO);
    }
}
