package com.ecommerce.ecommApp.recommend.recommendation.controller;

import com.ecommerce.ecommApp.Objects;
import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.pojo.ResponseMessage;
import com.ecommerce.ecommApp.commons.pojo.dto.ProductDto;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.recommend.recommendation.service.RecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.elasticsearch.core.internal.io.IOUtils.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class RecommendationControllerTest {

    @InjectMocks
    private RecommendationController recommendationController;

    @Mock
    private RecommendationService recommendationService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Objects objects;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(recommendationController)
                .setControllerAdvice()
                .build();
        this.objects = new Objects();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void fetchRecommendationTest() throws Exception {

        ProductDto productDto = objects.getProductDto();
        Set<ProductDto> products = new HashSet<>();
        products.add(productDto);

        when(recommendationService.fetchRecommendedProduct(anyLong())).thenReturn(products);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(CommonsUtil.RECOMMENDATION + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ResponseMessage ResponseMessage = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertEquals(200, result.getResponse().getStatus());
        assertTrue(((List<Product>)ResponseMessage.getData()).size() > 0);

    }

    @Test
    public void fetchRecommendationWrongMethodTest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(CommonsUtil.RECOMMENDATION + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertEquals(405, result.getResponse().getStatus());

    }

    @Test
    public void fetchRecommendationParamsTest_1() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(CommonsUtil.RECOMMENDATION + "/abc")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertEquals(400, result.getResponse().getStatus());

    }

    @Test
    public void fetchRecommendationParamsTest_2() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(CommonsUtil.RECOMMENDATION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertEquals(404, result.getResponse().getStatus());

    }
}
