package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

  @Autowired
  ProductService productService;

  @RequestMapping(value = "/products", method = RequestMethod.GET)
  private List<Product> getAllProducts() {
    List<Product> allProducts = new ArrayList<>();
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
}
