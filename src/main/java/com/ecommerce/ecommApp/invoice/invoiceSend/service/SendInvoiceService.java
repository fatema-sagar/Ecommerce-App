package com.ecommerce.ecommApp.invoice.invoiceSend.service;

import com.ecommerce.ecommApp.invoice.invoiceSend.dto.SendInvoiceDto;
import com.sendgrid.Response;

public interface SendInvoiceService {
    Response sendInvoice(SendInvoiceDto sendInvoiceDto);
}
