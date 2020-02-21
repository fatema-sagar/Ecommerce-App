package com.ecommerce.ecommApp.billGenerator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BillRequestDto {

    @NonNull
    @JsonProperty("customer_id")
    private Long customerId;

    @NonNull
    @JsonProperty("product_ids")
    private List<Long> productIds;

    public BillRequestDto() {
        this.productIds = new ArrayList<>();
    }

}
