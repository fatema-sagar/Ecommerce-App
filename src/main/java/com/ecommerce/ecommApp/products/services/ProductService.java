package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.pojo.orders.ItemsDTO;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.products.exceptions.NotEnoughQuantityException;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

  Logger logger = LoggerFactory.getLogger(ProductService.class);
  @Autowired
  ProductRepository productRepository;

  public List<Product> getProductsList() {
    return productRepository.findAll();
  }

  public Product createProduct(Product product) {
    logger.info("Adding Product {} to the db..", product);
    return productRepository.save(product);
  }

  public Product updateProduct(Product product) throws ElementNotFoundException {
    if (productRepository.existsById(product.getProductid())) {
      logger.info("Updating the product {}", product);
      return productRepository.save(product);
    } else {
      throw new ElementNotFoundException("Product ID is not available");
    }
  }

  public void deductProducts(List<ItemsDTO> product) throws ElementNotFoundException, NotEnoughQuantityException {
    Iterator iterator = product.iterator();
    while (iterator.hasNext()) {
      Product element = (Product) iterator.next();
      if (productRepository.existsById(element.getProductid())) {
        Product invent = productRepository.findById(element.getProductid()).get();
        if (invent.getQuantity() >= element.getQuantity()){
          element.setQuantity(invent.getQuantity() - element.getQuantity());
          productRepository.save(element);
        } else {
          throw new NotEnoughQuantityException("The product you are trying to update does not enough quantity");
        }
      } else {
        throw new ElementNotFoundException("Element does not exist");
      }
    }
  }

  public Product increaseProductCount(Product product) throws ElementNotFoundException {
    if (productRepository.existsById(product.getProductid())) {
      Product existingProduct = productRepository.findById(product.getProductid()).get();
      existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
      return productRepository.save(existingProduct);
    } else {
      throw new ElementNotFoundException("Unable to update the quantity for the product, as it is not available with the database.");
    }
  }

  public List<String> getAllCategories() {
    List<String> categories = productRepository.getCategory();
    return categories;
  }
}
