package com.ecommerce.ecommApp.recommend.recommendation.service;

import org.apache.kafka.streams.Topology;

import java.util.List;

public interface FetchViewProductsStream {
    List<Long> start(Long customerId);
    Topology createTopology(List<Long> list, Long customerId);
}
