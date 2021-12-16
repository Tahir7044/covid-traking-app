package com.microservice.limitsservice.controller;

import com.microservice.limitsservice.services.covid19Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    covid19Data locationStats;
    @GetMapping("/")
    public String home(Model model){{

        model.addAttribute("locationStats",locationStats.getLocationStatsList());
        model.addAttribute("totslCases",locationStats.getTotaCases());

    }
        return "Home";
    }
}
