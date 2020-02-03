package com.ecommerce.ecommApp.notifications.handlers;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderCancelled;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderPlaced;
import com.ecommerce.ecommApp.commons.pojo.notification.UserRegistered;
import com.ecommerce.ecommApp.commons.enums.NotificationType;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NotificationHandler implements Handler {

    private static final Logger log=LoggerFactory.getLogger(NotificationHandler.class);

    @Override
    public void sendNotification(String notifyingService,List<String> modes, Object object, String message) {
        if (modes.contains(NotificationType.Text_SMS.toString()))
            createSmsNotification(notifyingService, object, message);

        if (modes.contains(NotificationType.EMAIL.toString()))
            createEmailNotificaton(notifyingService, object, message);

        if (modes.contains(NotificationType.Text_WHATSAPP.toString()))
            createWhatsappNotification(notifyingService, object, message);

    }

    public void createSmsNotification(String notifyingService,Object object,String message) {
        switch(notifyingService)
        {
            case "User Registered":
                sendSmsNotification(((UserRegistered)(object)).getCustomerDto().getNumber(),message);
                break;
            case "Order Cancelled":
                sendSmsNotification(((OrderCancelled)(object)).getCustomerDto().getNumber(),message);
                break;
            case "Order Status":
                sendSmsNotification(((UserRegistered)(object)).getCustomerDto().getNumber(),message);
                break;
            case "Order Placed":
                sendSmsNotification(((OrderPlaced)(object)).getCustomerDto().getNumber(),message);
                break;
        }
    }

    public void createEmailNotificaton(String notifyingService, Object object,String message) {
        switch (notifyingService) {
            case "User Registered":
                sendEmailNotificaton(((UserRegistered) (object)).getCustomerDto().getEmail(), notifyingService, message);
                break;
            case "Order Cancelled":
                sendEmailNotificaton(((OrderCancelled) (object)).getCustomerDto().getEmail(), notifyingService, message);
                break;
            case "Order Status":
                sendEmailNotificaton(((UserRegistered) (object)).getCustomerDto().getEmail(), notifyingService, message);
                break;
            case "Order Placed":
                sendEmailNotificaton(((OrderPlaced) (object)).getCustomerDto().getEmail(), notifyingService, message);
                break;
        }
    }

    public void createWhatsappNotification(String notifyingService, Object object,String message) {

    }

    public void sendSmsNotification(Long number, String message) {
        if (number == null)
            return;

        try {
            Message sms = Message.creator(new PhoneNumber("+91" + number),
                    new PhoneNumber(EcommAppApplication.environment.getRequiredProperty("twilio.number")),
                    message).create();
            System.out.println("notification status : for " + number + " : is " + sms.getStatus());
        } catch (ApiException ex) {
            System.out.println("error from api side");
        }
    }

    public void sendEmailNotificaton(String email, String subject, String message) {

        if (email.trim().equals(""))
            return;

        Email from = new Email(EcommAppApplication.environment.getRequiredProperty("sendgrid.email.from"));
        Email to = new Email(email);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(EcommAppApplication.environment.getProperty("sendgrid.access.token"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("email : " + to + " : " + response.getStatusCode());
        } catch (Exception ex) {
            System.out.println("Exception : " + ex);
        }
    }

    public void sendWhatsappNotification(Long number, String message) {
        if (number == null)
            return;


    }
}
