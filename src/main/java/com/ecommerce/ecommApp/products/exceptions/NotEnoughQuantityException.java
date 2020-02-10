package com.ecommerce.ecommApp.products.exceptions;

public class NotEnoughQuantityException extends Exception{
  public NotEnoughQuantityException(String message) {
    super(message);
  }
}
