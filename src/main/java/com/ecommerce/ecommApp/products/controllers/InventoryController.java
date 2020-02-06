package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Inventory;
import com.ecommerce.ecommApp.products.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.products.services.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class InventoryController {

  Logger logger = LoggerFactory.getLogger(InventoryController.class);

  @Autowired
  InventoryService inventoryService;

  @RequestMapping(value = "/inventory/addproduct", method = RequestMethod.POST)
  public ResponseEntity<Inventory> addProductToInventory(@RequestBody Inventory inventory) {
    try {
      return new ResponseEntity<>(inventoryService.addToInventory(inventory), HttpStatus.OK);
    } catch (Exception e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/inventory/displayInventory", method = RequestMethod.GET)
  public ResponseEntity selectAllInventory() {
    try {
      return new ResponseEntity(inventoryService.getAllElements(), HttpStatus.OK);
    } catch (ElementNotFoundException e) {
      e.getMessage();
    }
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/inventory/updateInventory", method = RequestMethod.PUT)
  public ResponseEntity<Inventory> updateProductInInventory(@RequestBody Inventory inventory) {
    try {
      return new ResponseEntity<>(inventoryService.updateInventoryElement(inventory), HttpStatus.OK);
    } catch (ElementNotFoundException e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/inventory/deleteInventory/{inventoryid}", method = RequestMethod.DELETE)
  public ResponseEntity deleteProductFromInventory(@PathVariable long inventoryid) {
    try {
      inventoryService.deleteFromInventory(inventoryid);
      return new ResponseEntity<>( HttpStatus.OK);
    } catch (ElementNotFoundException e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/inventory/deductInventory", method = RequestMethod.PUT)
  public ResponseEntity deductFromInventory(@RequestBody Inventory[] inventory) {
    try {
      logger.info("fetching elements to deduct {}", inventory);
      inventoryService.deductInventory(Arrays.asList(inventory));
      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception e) {
      e.getMessage();
    }
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }

}
