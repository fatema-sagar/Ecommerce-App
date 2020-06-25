package com.ecommerce.ecommApp.invoice.invoiceGenerator.service;

import com.ecommerce.ecommApp.invoice.invoiceGenerator.dto.InvoiceFormatDto;

public interface PdfGenerateService {
    String generatePdf(InvoiceFormatDto invoiceFormatDto);
}
