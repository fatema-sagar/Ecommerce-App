package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.products.services.FeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product")
@RestController
public class FeatureController {

  private static final Logger logger = LoggerFactory.getLogger(FeatureController.class);

  @Autowired
  private FeatureService featureService;

  @GetMapping("/filterBy/{category}")
  private ResponseEntity<Object> filterByCategory(@PathVariable String category) {
    try {
      return new ResponseEntity(featureService.getByCategory(category), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity("The specified category is not available.. " +e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
