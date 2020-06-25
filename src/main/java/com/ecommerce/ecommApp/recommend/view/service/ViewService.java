package com.ecommerce.ecommApp.recommend.view.service;

import com.ecommerce.ecommApp.commons.pojo.ResponseMessage;
import com.ecommerce.ecommApp.recommend.view.dto.ViewProductDto;

public interface ViewService {
    ResponseMessage viewProduct(ViewProductDto viewProductDto);
}
