package com.ecommerce.ecommApp.products;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.Util.Communication;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMethod;


public final class ElasticSearchUtil {
    private static final String INET_ADDRESS = "localhost:9200";
    private static final String _INDEX = "products";
    private static final String _TYPE = "_doc";

    public static boolean insertProduct(Product product) {
        try {
            ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
            String json = objectMapper.writeValueAsString(product);
            String endpoint = String.format("%s/%s/%s/%s", INET_ADDRESS, _INDEX, _TYPE, product.getProductId());
            Communication.sendHttpRequest(endpoint, json, RequestMethod.POST);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static boolean updateProduct(Product product) {
        try {
            ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
            String json = objectMapper.writeValueAsString(product);
            String endpoint = String.format("%s%s%s%s", INET_ADDRESS, _INDEX, _TYPE, product.getProductId());
            Communication.sendHttpRequest(endpoint, json, RequestMethod.PUT);
            return true;
        } catch (JsonProcessingException ex) {
            return false;
        }
    }
}