package com.ecommerce.ecommApp.invoice.invoiceGenerator.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

public interface HeaderFooterService {
    void setHeader(String header);
    void onOpenDocument(PdfWriter writer, Document document);
    void onEndPage(PdfWriter writer, Document document);
    void onCloseDocument(PdfWriter writer, Document document);
}
