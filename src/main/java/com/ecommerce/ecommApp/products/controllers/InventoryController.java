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

@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

  @Autowired
  private InventoryService inventoryService;

  @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
  public ResponseEntity<Inventory> addProductToInventory(@RequestBody Inventory inventory) {
    try {
      return new ResponseEntity<>(inventoryService.addToInventory(inventory), HttpStatus.OK);
    } catch (Exception e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/displayInventory", method = RequestMethod.GET)
  public ResponseEntity selectAllInventory() {
    try {
      return new ResponseEntity(inventoryService.getAllElements(), HttpStatus.OK);
    } catch (ElementNotFoundException e) {
      e.getMessage();
    }
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/updateInventory", method = RequestMethod.PUT)
  public ResponseEntity<Inventory> updateProductInInventory(@RequestBody Inventory inventory) {
    try {
      return new ResponseEntity<>(inventoryService.updateInventoryElement(inventory), HttpStatus.OK);
    } catch (ElementNotFoundException e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/deleteInventory/{inventoryid}", method = RequestMethod.DELETE)
  public ResponseEntity deleteProductFromInventory(@PathVariable long inventoryid) {
    try {
      inventoryService.deleteFromInventory(inventoryid);
      return new ResponseEntity<>( HttpStatus.OK);
    } catch (ElementNotFoundException e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

}
