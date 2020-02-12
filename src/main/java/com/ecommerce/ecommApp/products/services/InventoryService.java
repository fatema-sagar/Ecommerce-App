package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.enums.ProductSize;
import com.ecommerce.ecommApp.commons.pojo.orders.ItemsDTO;
import com.ecommerce.ecommApp.commons.pojo.products.Inventory;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.products.exceptions.NotEnoughQuantityException;
import com.ecommerce.ecommApp.products.repositories.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class InventoryService {

  private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

  @Autowired
  InventoryRepository inventoryRepository;

  public Inventory addToInventory(Inventory inventory) {
    String size = inventory.getSize();
    inventory.setSize(String.valueOf(ProductSize.valueOf(size)));
    logger.info("Adding Inventory Item {} to the db..", inventory);
    return inventoryRepository.save(inventory);
  }

  public Inventory updateInventoryElement(Inventory inventory) throws ElementNotFoundException {
    if (inventoryRepository.existsById(inventory.getInventoryId())) {
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
      return allElements;
    } else {
      throw new ElementNotFoundException("Inventory is Empty...");
    }
  }

  public void deductProducts(List<ItemsDTO> product) throws ElementNotFoundException, NotEnoughQuantityException {
    for (ItemsDTO element : product) {
      if (inventoryRepository.existsById(element.getProductID())) {
        Inventory invent = inventoryRepository.findById(element.getProductID()).get();
        if (invent.getQuantity() >= element.getQuantity()) {
          invent.setQuantity(invent.getQuantity() - element.getQuantity());
          inventoryRepository.save(invent);
        } else {
          throw new NotEnoughQuantityException("The product you are trying to update does not enough quantity");
        }
      } else {
        throw new ElementNotFoundException("Element does not exist");
      }
    }
  }

  public Inventory increaseProductCount(Inventory inventory) throws ElementNotFoundException {
    if (inventoryRepository.existsById(inventory.getInventoryId())) {
      Inventory existingInventory = inventoryRepository.findById(inventory.getInventoryId()).get();
      existingInventory.setQuantity(existingInventory.getQuantity() + inventory.getQuantity());
      return inventoryRepository.save(existingInventory);
    } else {
      throw new ElementNotFoundException("Unable to update the quantity for the product, as it is not available with the database.");
    }
  }
}