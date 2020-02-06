package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.enums.Size;
import com.ecommerce.ecommApp.commons.pojo.products.Inventory;
import com.ecommerce.ecommApp.products.exceptions.InventoryElementNotFoundException;
import com.ecommerce.ecommApp.products.repositories.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;

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

  public Inventory updateInventoryElement(Inventory inventory) throws InventoryElementNotFoundException{
    if (inventoryRepository.existsById(inventory.getInventoryid())) {
      logger.info("Updating the Inventory Element {}", inventory);
      return inventoryRepository.save(inventory);
    } else {
      throw new InventoryElementNotFoundException("Element not found for update");
    }
  }

  public void deleteFromInventory(long inventoryId) throws InventoryElementNotFoundException {
    if (inventoryRepository.existsById(inventoryId)) {
      logger.info("Deleting product from id {}", inventoryId);
      inventoryRepository.deleteById(inventoryId);
    }
    else {
      throw new InventoryElementNotFoundException("Element Not Found. Enter correct Inventory Id to delete the element");
    }
  }

}
