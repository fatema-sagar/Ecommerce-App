package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.services.FeatureService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @PostMapping("/search")
  private ResponseEntity<Object> searchProducts(@RequestBody JSONObject jsonObject) {
    try {
      List<Product> searchedProducts = featureService.getSearchedElements(jsonObject);
      return new ResponseEntity(searchedProducts, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error while searching" + e.getMessage());
      return new ResponseEntity<>("Error while searching", HttpStatus.NOT_FOUND);
    }
  }
}
