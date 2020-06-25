package com.ecommerce.ecommApp.invoice.invoiceGenerator.service;

import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.sendgrid.Response;

public interface InvoiceGeneratorService {
    Response invoiceGenerate(OrdersDTO ordersDTO);
}
