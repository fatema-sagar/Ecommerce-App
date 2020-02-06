package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.products.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {

  @Autowired
  ProductService productService;

  Logger logger = LoggerFactory.getLogger(ProductController.class);

  @RequestMapping(value = "/products", method = RequestMethod.GET)
  private List<Product> getAllProducts() {
    List<Product> allProducts;
    allProducts = productService.getProductsList();
    return allProducts;
  }

  @RequestMapping(value = "/product/add", method = RequestMethod.POST)
  private ResponseEntity<Product> addProduct(@RequestBody Product product) {
    try{
      return new ResponseEntity<>(productService.createProduct(product), HttpStatus.OK);
    } catch (Exception e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/product/update", method = RequestMethod.PUT)
  private ResponseEntity<Product> updateProduct(@RequestBody Product product) {
    try {
      return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.OK);
    } catch (ElementNotFoundException e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/inventory/deductInventory", method = RequestMethod.PUT)
  public ResponseEntity deductFromInventory(@RequestBody Product[] products) {
    try {
      logger.info("fetching elements to deduct {}", products);
      productService.deductProducts(Arrays.asList(products));
      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception e) {
      e.getMessage();
    }
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }
}
