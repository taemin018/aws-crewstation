package com.example.crewstation.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("")
    public String goAdminPage(){

        return "admin/main";
    }

    @GetMapping("/join")
    public String goJoinPage(){
        return "admin/join";
    }

    @GetMapping("/login")
    public String goLoginPage(){
        return "admin/login";
    }
}
