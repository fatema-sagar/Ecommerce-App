package com.ecommerce.ecommApp.recommend.view.service;

import com.ecommerce.ecommApp.recommend.view.dto.ViewProductDto;

import java.util.List;

public interface FetchViewedProduct {
    List<ViewProductDto> getViewProduct(String customerId);
}
