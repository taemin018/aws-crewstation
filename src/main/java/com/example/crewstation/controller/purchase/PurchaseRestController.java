package com.example.crewstation.controller.purchase;

import com.example.crewstation.dto.purchase.PurchaseCriteriaDTO;
import com.example.crewstation.service.purchase.PurchaseService;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/gifts/**")
public class PurchaseRestController {
    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<PurchaseCriteriaDTO> getPurchases(@RequestParam int page,@RequestParam String keyword) {
        Search search = new Search();
        search.setPage(page);
        search.setKeyword(keyword);
        PurchaseCriteriaDTO purchases = purchaseService.getPurchases(search);
        return ResponseEntity.ok().body(purchases);
    }

}
