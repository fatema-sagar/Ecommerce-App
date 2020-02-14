package com.ecommerce.ecommApp.products;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.Util.Communication;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


public final class ElasticSearchUtil {

    private static final Logger log = LoggerFactory.getLogger(ElasticSearchUtil.class);
    private static final String INET_ADDRESS = "http://localhost:9200";
    private static final String _INDEX = "products";
    private static final String _TYPE = "_doc";

    public static boolean insertProduct(Product product) {
        try {
            ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
            String json = objectMapper.writeValueAsString(product);
            String endpoint = String.format("%s/%s/%s/%s", INET_ADDRESS, _INDEX, _TYPE, product.getProductId());
            log.info("Data inserted in elastic search : {} : {} " + endpoint, json);
            String response = Communication.sendHttpRequest(endpoint, json, RequestMethod.POST);
            log.trace("Response : {} ", response);
            return true;
        } catch (Exception ex) {
            log.error("Error in inserting data into the elastic seach : {} ", ex);
            return false;
        }
    }

    public static boolean updateProduct(Product product) {
        try {
            ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
            String productJson = objectMapper.writeValueAsString(product);
            String jsonBody = String.format("{\"doc\":%s}", productJson);
            String endpoint = String.format("%s/%s/%s/%s", INET_ADDRESS, _INDEX, "_update", product.getProductId());
            System.out.println(Communication.sendHttpRequest(endpoint, jsonBody, RequestMethod.POST));
            log.info("Data updated in elastic seach for product {}", product.getProductId());
            return true;
        } catch (JsonProcessingException ex) {
            log.error("Error in Updating data into the Elastic seach : {} ", ex);
            return false;
        }
    }

    public static boolean deleteProduct(long id) {
        String endpoint = String.format("%s/%s/%s/%s", INET_ADDRESS, _INDEX, _TYPE, id);
        try {
            String response = Communication.sendDeleteRequest(endpoint);
            log.trace("product deted for id : {} ", id);
            return true;
        } catch (Exception ex) {
            log.error("Error in Deleting prouct for Product ID {} ", ex);
            return false;
        }
    }

    public static List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
        String endPoint = String.format("%s/%s/%s?q=*", INET_ADDRESS, _INDEX, "_search");
        String response = Communication.sendGetRequest(endPoint);
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                object = object.getJSONObject("_source");
                String productJson = object.toString();
                Product product = objectMapper.readValue(productJson, Product.class);
                list.add(product);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws JSONException {
        for (int i = 0; i < 10; i++)
        {
            Product product = new Product();
            product.setName("Jeans");
            product.setProductId(i);
            product.setCategory("Bottoms");
            product.setSize("XL");
            product.setImage("1234asdfg");
            product.setProductDescription("product detail description");
            product.setPrice(123);
            insertProduct(product);
        }
//        deleteProduct(1);
//        getAllProducts();
//        searchProduct();
        String json = "{\"search_text\":\"jeans\",\"price\":{\"lte\":500,\"gte\":13},\"category\":\"bottoms\"}";
        QueryBuilder queryBuilder = new QueryBuilder(json);
        String jsonBody = queryBuilder.build();
        searchProduct(jsonBody);
    }


    private static List<Product> searchProduct(String jsonBody) {
        try {
            List<Product> allProducts = new ArrayList<>();
            log.info("Searching all products");
            ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
            String endpoint = String.format("%s/%s/%s", INET_ADDRESS, _INDEX, "_search");
            System.out.println(Communication.sendHttpRequest(endpoint, jsonBody, RequestMethod.GET));
            return allProducts;
        } catch (Exception e) {
            return null;
        }
    }


}

class QueryBuilder {

    private JSONObject jsonQuery = new JSONObject("{\"query\":{\"bool\":{}}}");
    private JSONObject jsonObject;

    public QueryBuilder(String json) throws JSONException {
        jsonObject = new JSONObject(json);
    }

    public String build() throws JSONException {

        if (jsonObject.has("price")) {
            JSONObject range = jsonObject.getJSONObject("price");
            int gte = range.getInt("gte");
            int lte = range.getInt("lte");
            String filter = String.format("{\"range\":{\"price\":{\"gte\":%s,\"lte\":%s}}}", gte, lte);
            JSONObject filterObject = new JSONObject(filter);
            jsonQuery.getJSONObject("query").getJSONObject("bool").put("filter", filterObject);
        }

        if (jsonObject.has("category")) {
            String categoryValue = jsonObject.getString("category");
            String mustValue = String.format("[{\"match\":{\"category\":{\"query\":\"%s\",\"fuzziness\":\"AUTO\"}}}]", categoryValue);
            JSONArray mustArray = new JSONArray(mustValue);
            jsonQuery.getJSONObject("query").getJSONObject("bool").put("must", mustArray);
        }

        if (jsonObject.has("search_text")) {
            String text = jsonObject.getString("search_text");
            if (!jsonObject.has("category")) {
                //no category object
                String shouldValue = String.format("[{\"match\":{\"product_name\":{\"query\":\"%s\",\"fuzziness\":\"AUTO\"}}},{\"match\":{\"product_description\":{\"query\":\"%s\",\"fuzziness\":\"AUTO\"}}},{\"match\":{\"category\":{\"query\":\"%s\",\"fuzziness\":\"AUTO\"}}}]"
                        , text, text, text);
                JSONArray shouldArray = new JSONArray(shouldValue);
                jsonQuery.getJSONObject("query").getJSONObject("bool").put("should", shouldArray);
            } else {
                //it has category
                String shouldValue = String.format("[{\"match\":{\"product_name\":{\"query\":\"%s\",\"fuzziness\":\"AUTO\"}}},{\"match\":{\"product_description\":{\"query\":\"%s\",\"fuzziness\":\"AUTO\"}}}]"
                        , text, text);
                JSONArray shouldArray = new JSONArray(shouldValue);
                jsonQuery.getJSONObject("query").getJSONObject("bool").put("should", shouldArray);
            }
            jsonQuery.getJSONObject("query").getJSONObject("bool").put("minimum_should_match", 1);
        }
        return jsonQuery.toString();
    }
}