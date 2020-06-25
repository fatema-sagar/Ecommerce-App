package com.ecommerce.ecommApp.invoice.invoiceGenrator.service;

import com.ecommerce.ecommApp.Objects;
import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.customers.services.CustomerService;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.converter.OrderDtoToInvoiceFormat;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.dto.InvoiceFormatDto;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.service.InvoiceGeneratorServiceImpl;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.service.PdfGenerateServiceImpl;
import com.ecommerce.ecommApp.invoice.invoiceSend.service.SendInvoiceServiceImpl;
import com.sendgrid.Response;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceGeneratorServiceImplTest {

    @InjectMocks
    private InvoiceGeneratorServiceImpl invoiceGeneratorServiceImpl;

    @Mock
    private PdfGenerateServiceImpl pdfGenerateServiceImpl;

    @Mock
    private OrderDtoToInvoiceFormat orderDtoToInvoiceFormat;

    @Mock
    private CustomerService customerService;

    @Mock
    private SendInvoiceServiceImpl sendInvoiceServiceImpl;

    private Objects objects;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.objects = new Objects();
    }

    @Test
    public void invoiceGenerateTest() throws NotFoundException {

        InvoiceFormatDto invoiceFormatDto = objects.getInvoiceFormatDto();
        String filePath = objects.getFilePath();
        CustomerDto customerDto = objects.getCustomerDto();

        when(orderDtoToInvoiceFormat.convertToInvoiceFormatDto(any())).thenReturn(invoiceFormatDto);
        when(pdfGenerateServiceImpl.generatePdf(any())).thenReturn(filePath);
        when(customerService.getCustomerDetails(anyLong())).thenReturn(customerDto);
        when(sendInvoiceServiceImpl.sendInvoice(any())).thenReturn(new Response());

        invoiceGeneratorServiceImpl.invoiceGenerate(any());

        verify(customerService, times(1)).getCustomerDetails(anyLong());
        verify(sendInvoiceServiceImpl, times(1)).sendInvoice(any());

    }

    @Test
    public void invoiceGenerateExceptionTest() throws NotFoundException {

        InvoiceFormatDto invoiceFormatDto = objects.getInvoiceFormatDto();
        String filePath = objects.getFilePath();

        when(orderDtoToInvoiceFormat.convertToInvoiceFormatDto(any())).thenReturn(invoiceFormatDto);
        when(pdfGenerateServiceImpl.generatePdf(any())).thenReturn(filePath);
        when(customerService.getCustomerDetails(anyLong())).thenThrow(NotFoundException.class);
        when(sendInvoiceServiceImpl.sendInvoice(any())).thenReturn(new Response());

        invoiceGeneratorServiceImpl.invoiceGenerate(any());

        verify(customerService, times(1)).getCustomerDetails(anyLong());
        verify(sendInvoiceServiceImpl, times(1)).sendInvoice(any());

    }

}
