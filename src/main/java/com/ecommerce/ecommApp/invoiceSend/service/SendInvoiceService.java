package com.ecommerce.ecommApp.invoiceSend.service;

import com.ecommerce.ecommApp.invoiceSend.dto.SendInvoiceDto;
import com.ecommerce.ecommApp.invoiceSend.utils.SendUtils;
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
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class SendInvoiceService {

    @Autowired
    private SendGridAPI sendGridAPI;


    public Response sendInvoice(SendInvoiceDto sendInvoiceDto) throws IOException {

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
        request.setBody(mail.build());

        Response response = sendGridAPI.api(request);

        log.trace("Send mail with response code : " + response.getStatusCode());

        return response;
    }

    private Attachments getAttachments(File file) throws IOException {

        Attachments attachment = new Attachments();

        attachment.setContent(convertFileToBase64(Files.toByteArray(file)));
        attachment.setType(SendUtils.SEND_TYPE);
        attachment.setFilename(SendUtils.FILE_NAME + SendUtils.FILE_EXTENSION);
        return attachment;
    }

    private String convertFileToBase64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }
}
