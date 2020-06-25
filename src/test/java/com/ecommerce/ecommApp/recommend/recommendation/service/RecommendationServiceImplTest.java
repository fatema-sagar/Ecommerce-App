package com.ecommerce.ecommApp.recommend.recommendation.service;

import com.ecommerce.ecommApp.Objects;
import com.ecommerce.ecommApp.commons.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.commons.pojo.dto.ProductDto;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.products.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecommendationServiceImplTest {

    @InjectMocks
    private RecommendationServiceImpl recommendationServiceImpl;

    @Mock
    private ProductService productService;

    @Mock
    private FetchViewProductsStreamImpl viewProductsStream;

    @Mock
    private ModelMapper modelMapper;

    private Objects objects;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.objects = new Objects();
    }

    @Test
    public void fetchRecommendedProductTest() throws ElementNotFoundException, InterruptedException {

        Product product = objects.getProduct();

        List<Long> viewProductList = new ArrayList<>();
        viewProductList.add(1L);

        Set<ProductDto> productDtoSet = new HashSet<>();
        productDtoSet.add(objects.getProductDto());


        when(viewProductsStream.start(anyLong())).thenReturn(viewProductList);
        when(productService.getProduct(anyLong())).thenReturn(product);
        when(modelMapper.map(anySet(), any(Type.class))).thenReturn(productDtoSet);

        Set<ProductDto> products = recommendationServiceImpl.fetchRecommendedProduct(1L);

        verify(productService, times(1)).getProduct(anyLong());
        assertTrue(productDtoSet.size() == products.size());
    }

    @Test
    public void fetchRecommendedProductExceptionTest_1() throws ElementNotFoundException, InterruptedException {

        List<Long> viewProductList = new ArrayList<>();
        viewProductList.add(1L);

        when(viewProductsStream.start(anyLong())).thenReturn(viewProductList);
        when(productService.getProduct(anyLong())).thenThrow(ElementNotFoundException.class);

        Set<ProductDto> products = recommendationServiceImpl.fetchRecommendedProduct(1L);
        assertTrue(products.size() == 0);
        verify(viewProductsStream, times(1)).start(anyLong());

    }

    @Test
    public void fetchRecommendedProductExceptionTest_2() throws ElementNotFoundException, InterruptedException {

        List<Long> viewProductList = new ArrayList<>();
        viewProductList.add(1L);

        when(viewProductsStream.start(anyLong())).thenReturn(viewProductList);
        when(productService.getProduct(anyLong())).thenThrow(InterruptedException.class);

        recommendationServiceImpl.fetchRecommendedProduct(1L);

        verify(productService, times(1)).getProduct(anyLong());
    }


}
