package com.ecommerce.ecommApp.recommend.recommendation.service;

import com.ecommerce.ecommApp.commons.pojo.dto.ProductDto;

import java.util.Set;

public interface RecommendationService {
    Set<ProductDto> fetchRecommendedProduct(Long customerId);
}
