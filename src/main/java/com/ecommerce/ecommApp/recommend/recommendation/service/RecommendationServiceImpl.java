package com.ecommerce.ecommApp.recommend.recommendation.service;

import com.ecommerce.ecommApp.commons.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.commons.pojo.dto.ProductDto;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.elasticsearch.ElasticsearchObject;
import com.ecommerce.ecommApp.products.elasticsearch.ElasticSearchUtil;
import com.ecommerce.ecommApp.products.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RecommendationServiceImpl implements RecommendationService {

    private FetchViewProductsStreamImpl viewProductsStream;
    private ProductService productService;
    private ModelMapper modelMapper;
    private ElasticSearchUtil elasticSearchUtil;

    /**
     * constructor for initialize the variable
     * @param viewProductsStream
     * @param productService
     * @param modelMapper
     */
    @Autowired
    public RecommendationServiceImpl(FetchViewProductsStreamImpl viewProductsStream,
                                     ProductService productService, ModelMapper modelMapper) {
        this.viewProductsStream = viewProductsStream;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.elasticSearchUtil = ElasticsearchObject.getElasticsearchObject();
    }


    /**
     * method used to fetch the recommended product for user
     * @param customerId for filter the view product of same user
     * @return set of product what is recommended for user
     */
    public Set<ProductDto> fetchRecommendedProduct(Long customerId) {

        List<Long> viewProductList;
        Set<ProductDto> products = new HashSet<>();
        viewProductList = viewProductsStream.start(customerId);
        viewProductList.forEach(productId -> {
            try {

                Product product = productService.getProduct(Long.valueOf(productId));
                Type setType = new TypeToken<Set<ProductDto>>() {}.getType();
                Set<ProductDto> productDtoSet =
                        modelMapper.map(searchProductElasticSearch(product), setType);
                products.addAll(productDtoSet);

            } catch (ElementNotFoundException | InterruptedException e) {

                log.warn("Element not fount with product id {}", productId);
            }
        });
        return products;
    }

    /**
     * method used to fetch the similar product
     * @param product product what we have to search in elastic search for recommended product
     * @return set of recommended product
     */
    private Set<Product> searchProductElasticSearch(Product product) {
        Set<Product> products = new HashSet<>();
        JSONObject jsonObject = new JSONObject();

        try {

            products.add(product);
            jsonObject.put("brand", product.getBrand());
            jsonObject.put("category", product.getCategory());
            products.addAll(elasticSearchUtil.searchProduct(jsonObject.toString()));
            jsonObject.put("name", product.getCategory());
            products.addAll(elasticSearchUtil.searchProduct(jsonObject.toString()));

        } catch (JSONException e) {

            log.error("Json exception with message {} and cause {}: ", e.getMessage(), e.getCause());
        }
       return products;
    }
}
