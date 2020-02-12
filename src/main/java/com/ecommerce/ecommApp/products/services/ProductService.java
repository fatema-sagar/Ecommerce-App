package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    ProductRepository productRepository;

    public List<Product> getProductsList() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        logger.info("Adding the following Product {} to the db..", product);
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) throws ElementNotFoundException {
        if (productRepository.existsById(product.getProductId())) {
            logger.info("Updated Product: {}", product);
            return productRepository.save(product);
        } else {
            throw new ElementNotFoundException("Product ID is not available");
        }
    }

    public List<String> getAllCategories() {
        return productRepository.getCategory();
    }

    public Product getProduct(long productId) throws ElementNotFoundException {
        if (productRepository.existsById(productId)) {
            return productRepository.findById(productId).get();
        } else {
            throw new ElementNotFoundException("ProductId not found");
        }
    }
}
