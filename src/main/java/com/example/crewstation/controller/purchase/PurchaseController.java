package com.example.crewstation.controller.purchase;

import com.example.crewstation.common.exception.PurchaseNotFoundException;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/gifts/**")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {
    private final PurchaseService purchaseService;
    @GetMapping("")
    public String list(){
        return "gift-shop/list";
    }
    @GetMapping("{postId}")
    public String detail(@PathVariable Long postId, Model model){
        Optional<PurchaseDetailDTO> purchase = purchaseService.getPurchase(postId);

        model.addAttribute("purchase", purchase.orElseThrow(PurchaseNotFoundException::new));
        return "gift-shop/detail";
    }
}
