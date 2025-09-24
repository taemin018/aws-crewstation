package com.example.crewstation.service.purchase;

import com.example.crewstation.dto.purchase.PurchaseCriteriaDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.DateUtils;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseDAO purchaseDAO;

    @Override
    public PurchaseCriteriaDTO getPurchases(Search search) {
        PurchaseCriteriaDTO purchaseCriteriaDTO = new PurchaseCriteriaDTO();
        int page = search.getPage();
        Criteria criteria = new Criteria(page, purchaseDAO.findCountAllByKeyWord(search));
        List<PurchaseDTO> purchases = purchaseDAO.findAllByKeyWord(criteria, search);
        purchases.forEach(purchase -> {
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
}
