package com.ecommerce.ecommApp.billGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfFormatDto {

    private Long invoiceId;

    private String orderId;

    private Long customerId;

    private String customerName;

    private Long productId;

    private String productName;

    private Long quantity;

    private String soldBy;

    private Double cost;

    private String shippingAddress;






}
