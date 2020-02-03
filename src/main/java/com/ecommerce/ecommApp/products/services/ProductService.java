package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

  Logger logger = LoggerFactory.getLogger(ProductService.class);
  @Autowired
  ProductRepository productRepository;

  public List<Product> getProductsList() {
    List<Product> productList;
    productList = productRepository.findAll();
    return productList;
  }

  public Product createProduct(Product product) {
    logger.info("Adding Product {} to the db..", product);
    return productRepository.save(product);
  }
}
