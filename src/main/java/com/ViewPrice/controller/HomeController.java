package com.ViewPrice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public  String home () {
        return "Wellcome to treading platform";
    }

    @GetMapping("/api")
    public  String secure () {
        return "Wellcome to treading platform Secure";
    }
}
