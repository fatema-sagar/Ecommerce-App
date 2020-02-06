package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.enums.Size;
import com.ecommerce.ecommApp.commons.pojo.products.Inventory;
import com.ecommerce.ecommApp.products.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.products.exceptions.NotEnoughQuantityException;
import com.ecommerce.ecommApp.products.repositories.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

  Logger logger = LoggerFactory.getLogger(InventoryService.class);

  @Autowired
  InventoryRepository inventoryRepository;

  public Inventory addToInventory(Inventory inventory) {
    String size = inventory.getSize();
    inventory.setSize(String.valueOf(Size.valueOf(size)));
    logger.info("Adding Inventory Item {} to the db..", inventory);
    return inventoryRepository.save(inventory);
  }

  public Inventory updateInventoryElement(Inventory inventory) throws ElementNotFoundException {
    if (inventoryRepository.existsById(inventory.getInventoryid())) {
      logger.info("Updating the Inventory Element {}", inventory);
      return inventoryRepository.save(inventory);
    } else {
      throw new ElementNotFoundException("Element not found for update");
    }
  }

  public void deleteFromInventory(long inventoryId) throws ElementNotFoundException {
    if (inventoryRepository.existsById(inventoryId)) {
      logger.info("Deleting product from id {}", inventoryId);
      inventoryRepository.deleteById(inventoryId);
    }
    else {
      throw new ElementNotFoundException("Element Not Found. Enter correct Inventory Id to delete the element");
    }
  }

  public List<Inventory> getAllElements() throws ElementNotFoundException {
    List<Inventory> allElements = inventoryRepository.findAll();
    if (allElements.size() != 0) {
      logger.info("Retrieving all the elements.");
      return allElements;
    } else {
      throw new ElementNotFoundException("Inventory is Empty...");
    }
  }

  public void deductInventory(List<Inventory> inventory) throws ElementNotFoundException, NotEnoughQuantityException {
    Iterator iterator = inventory.iterator();
    while (iterator.hasNext()) {
      Inventory element = (Inventory) iterator.next();
      if (inventoryRepository.existsById(element.getInventoryid())) {
        Inventory invent = inventoryRepository.findById(element.getInventoryid()).get();
        if (invent.getQuantity() > element.getQuantity()){
          element.setQuantity(invent.getQuantity() - element.getQuantity());
          inventoryRepository.save(element);
        } else {
          throw new NotEnoughQuantityException("The inventory you are trying to update does not enough quantity");
        }
      } else {
        throw new ElementNotFoundException("Element does not exist");
      }
    }
  }
}
