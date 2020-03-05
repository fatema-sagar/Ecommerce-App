package com.ecommerce.ecommApp.invoice.invoiceSend.service;

import com.ecommerce.ecommApp.invoice.invoiceSend.dto.SendInvoiceDto;
import com.ecommerce.ecommApp.invoice.invoiceSend.utils.SendUtils;
import com.google.common.io.Files;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGridAPI;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class SendInvoiceService {

    @Autowired
    private SendGridAPI sendGridAPI;

    /**
     * this method send the invoice to user email
     *
     * @param sendInvoiceDto the invoice details send to be user
     * @return Response after send the invoice to user
     */
    public Response sendInvoice(SendInvoiceDto sendInvoiceDto) {

        Response response = new Response();

        if(sendInvoiceDto == null) {
            response.setStatusCode(404);
            return response;
        }

        Email from = new Email(SendUtils.VENDOR_EMAIL, SendUtils.VENDOR_NAME);
        Email to = new Email(sendInvoiceDto.getTo(), sendInvoiceDto.getCustomerName());
        String message = sendInvoiceDto.getContentMessage();
        String subject = sendInvoiceDto.getSubject();
        Content content = new Content(SendUtils.CONTENT_TYPE, message);

        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();

        mail.addAttachments(getAttachments(sendInvoiceDto.getFile()));

        request.setMethod(Method.POST);
        request.setEndpoint(SendUtils.END_POINT);

        try {

            request.setBody(mail.build());
            response = sendGridAPI.api(request);

        } catch (IOException e) {
            log.error("Exception with build or send the mail with message : {}", e.getMessage());
            log.error("Exception with cause : ", e.getCause());
        }


        log.trace("Send mail with response code : " + response.getStatusCode());

        return response;
    }

    /**
     * this method create object of attachment file
     *
     * @param file the file to be attach with the object of attachment
     * @return the object of Attachment
     */
    private Attachments getAttachments(File file) {

        Attachments attachment = new Attachments();

        try {
            attachment.setContent(convertFileToBase64(Files.toByteArray(file)));
            attachment.setType(SendUtils.SEND_TYPE);
            attachment.setFilename(SendUtils.FILE_NAME + SendUtils.FILE_EXTENSION);

        } catch (IOException e) {
            log.error("File not found for attaching the customer invoice ,file path is  : {}", file.getPath());
            log.error("File not found or unable to convert to bytes with exception message {}  Cause : {}", e.getMessage(), e.getCause());
        }

        return attachment;
    }

    /**
     * method convert bytes to Base64 encoder
     *
     * @param input array of bytes for convert Base64 encoder
     * @return encoded byte to string
     */
    private String convertFileToBase64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }
}
