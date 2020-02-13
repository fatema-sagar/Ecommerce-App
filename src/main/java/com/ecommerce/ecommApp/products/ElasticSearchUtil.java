package com.ecommerce.ecommApp.products;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.Util.Communication;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMethod;


public final class ElasticSearchUtil {
    private static final String INET_ADDRESS = "localhost:9200";
    private static final String _INDEX = "products2";
    private static final String _TYPE = "_doc";

    public static boolean insertProduct(Product product) {
        try {
            ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
            String json = objectMapper.writeValueAsString(product);
            String endpoint = String.format("http://%s/%s/%s/%s", INET_ADDRESS, _INDEX, _TYPE, product.getProductId());
            System.out.println(endpoint);
            System.out.println(json);
            System.out.println(Communication.sendHttpRequest(endpoint, json, RequestMethod.POST));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static boolean updateProduct(Product product) {
        try {
            ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
            String productJson = objectMapper.writeValueAsString(product);
            String jsonBody=String.format("{\"doc\":%s}",productJson);
            System.out.println(jsonBody);
            String endpoint = String.format("http://%s%s%s%s", INET_ADDRESS, _INDEX, "_update", product.getProductId());
            Communication.sendHttpRequest(endpoint, jsonBody, RequestMethod.PUT);
            return true;
        } catch (JsonProcessingException ex) {
            return false;
        }
    }

    public static void main(String[] args) {
        Product product = new Product();
        product.setName("Jeans");
        product.setProductId(245);
        product.setCategory("Bottoms");
        product.setSize("XL");
        product.setImage("abc");
        product.setProductDescription("shgfsdukfusdfus");
        product.setPrice(653);
        System.out.println(insertProduct(product));

//        product.setName("Ripped Jeans");
//        updateProduct(product);
    }
}
