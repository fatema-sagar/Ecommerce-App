package com.ecommerce.ecommApp.recommendation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recommendation")
public class RecommendationController {

    @GetMapping("/fetch")
    public ResponseEntity fetchRecommendation() {
//        List<Product> products = recommendationService
        return new ResponseEntity("", HttpStatus.OK);
    }
}
