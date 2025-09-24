package com.example.crewstation.controller.purchase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gifts/**")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {

    @GetMapping("")
    public String purchase(){
        return "gift-shop/list";
    }

}
