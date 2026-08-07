package com.turtlesltd.sothikbhara;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboardController {

    @GetMapping("/")
    public String mainDashborad(){
        return "mainDashboard";
    }
}
