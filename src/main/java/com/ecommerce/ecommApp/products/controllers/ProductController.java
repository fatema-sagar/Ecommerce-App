package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.orders.ItemsDTO;
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

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(path = "/display", method = RequestMethod.GET)
    private List<Product> getAllProducts() {
        return  productService.getProductsList();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    private ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.createProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    private ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/increaseProduct", method = RequestMethod.PUT)
    public ResponseEntity addToAvailableProducts(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.increaseProductCount(product), HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            e.getMessage();
        }
        return new ResponseEntity<>("Unable to increase the quantity of existing product.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/category", method = RequestMethod.GET)
    public ResponseEntity displayCategory() {
        try {
            logger.info("fetching categories from the db...");
            return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseEntity<>("Unable to select categories", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/deductInventory", method = RequestMethod.PUT)
    public ResponseEntity deductFromInventory(@RequestBody List<ItemsDTO> products) {
        try {
            logger.info("fetching elements to deduct {}", products);
            productService.deductProducts(products);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
