package com.ecommerce.ecommApp.recommendation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recommendation")
public class RecommendationController {

    @GetMapping("/fetch")
    public ResponseEntity fetchRecommendation() {
        return new ResponseEntity("", HttpStatus.OK);
    }
}
