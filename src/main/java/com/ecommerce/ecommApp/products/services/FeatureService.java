package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.ElasticSearchUtil;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {

  @Autowired
  private ProductRepository productRepository;

  public List<Product> getByCategory(String category) {
    List<Product> sortedProducts = productRepository.getElementsByCategory(category);
    return sortedProducts;
  }

  public List<Product> getSearchedElements(String searchQuery) {
    List<Product> searchedProducts = ElasticSearchUtil.searchProduct(searchQuery);
    return searchedProducts;
  }

}
