package com.ecommerce.ecommApp.recommendation.service;

import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.kafka.consumer.FetchViewedProduct;
import com.ecommerce.ecommApp.kafka.stream.FetchViewProductsStream;
import com.ecommerce.ecommApp.products.ElasticSearchUtil;
import com.ecommerce.ecommApp.view.dto.ViewProductDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RecommendationService {

    @Autowired
    private FetchViewedProduct fetchViewedProduct;

    @Autowired
    private FetchViewProductsStream viewProductsStream;


    public Set<Product> fetchRecommendedProduct(String customerId) {

        List<ViewProductDto> viewProductList;
        Set<Product> products = new HashSet<>();
        viewProductList = viewProductsStream.start(customerId);
//        viewProductList = fetchViewedProduct.getViewProduct(customerId);
        viewProductList.forEach(product -> products.addAll(searchProductElasticSearch(product)));
        return products;
    }

    private Set<Product> searchProductElasticSearch(ViewProductDto viewProduct) {
        Set<Product> products = new HashSet<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productId", viewProduct.getProductId());
            products.addAll(ElasticSearchUtil.searchProduct(jsonObject.toString()));
            jsonObject.remove("productId");
            jsonObject.put("brand", viewProduct.getBrand());
            jsonObject.put("product_name", viewProduct.getName());
            products.addAll(ElasticSearchUtil.searchProduct(jsonObject.toString()));
            jsonObject.put("category", viewProduct.getCategory());
            products.addAll(ElasticSearchUtil.searchProduct(jsonObject.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return products;
    }

}
