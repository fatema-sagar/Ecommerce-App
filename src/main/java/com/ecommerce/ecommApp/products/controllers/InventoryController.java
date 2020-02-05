package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Inventory;
import com.ecommerce.ecommApp.products.exceptions.InventoryElementNotFoundException;
import com.ecommerce.ecommApp.products.services.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @RequestMapping(value = "/inventory/deleteInventory/{inventoryid}", method = RequestMethod.DELETE)
  public ResponseEntity deleteProductFromInventory(@PathVariable int inventoryid) {
    try {
      inventoryService.deleteFromInventory(inventoryid);
      return new ResponseEntity<>( HttpStatus.OK);
    } catch (InventoryElementNotFoundException e) {
      e.getMessage();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

}
