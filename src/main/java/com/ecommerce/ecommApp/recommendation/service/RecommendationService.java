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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RecommendationService {

    @Autowired
    private FetchViewedProduct fetchViewedProduct;

    @Autowired
    private FetchViewProductsStream viewProductsStream;


    public List<ViewProductDto> fetchRecommendedProduct(String customerId) {

        List<ViewProductDto> viewProductList;
        List<Product> products = new ArrayList<>();
        viewProductList = viewProductsStream.start(customerId);
//        viewProductList = fetchViewedProduct.getViewProduct(customerId);
//        viewProductList.forEach(product -> products.addAll(searchProductElasticSearch(product)));
        return viewProductList;
    }

    private List<Product> searchProductElasticSearch(ViewProductDto viewProduct) {
        List<Product> products = null;
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
