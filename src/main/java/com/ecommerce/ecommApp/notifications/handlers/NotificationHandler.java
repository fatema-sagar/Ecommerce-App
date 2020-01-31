package com.ecommerce.ecommApp.notifications.handlers;

import com.ecommerce.ecommApp.commons.pojo.notification.UserRegistered;
import com.ecommerce.ecommApp.notifications.NotificationType;

import java.util.List;

public class NotificationHandler implements Handler {

    @Override
    public void sendNotification(String notifyingService,List<String> modes, Object object, String message) {
        if (modes.contains(NotificationType.Text_SMS.toString()))
            createSmsNotification(notifyingService,object,message);

        if (modes.contains(NotificationType.EMAIL.toString()))
            createEmailNotificaton(notifyingService,object,message);

        if (modes.contains(NotificationType.TEXT_WHATSAPP.toString()))
            createWhatsappNotification(notifyingService,object,message);

    }

    public void createSmsNotification(String notifyingService,Object object,String message) {
        switch(notifyingService)
        {
            case "UserRegisteredNS":
                sendSmsNotification(((UserRegistered)(object)).getCustomer().getNumber(),message);
                break;
            case "OrderCancelledNS":
                sendSmsNotification(((UserRegistered)(object)).getCustomer().getNumber(),message);
                break;
            case "OrderStatusNS":
                sendSmsNotification(((UserRegistered)(object)).getCustomer().getNumber(),message);
                break;
            case "OrderPlacedNS":
                sendSmsNotification(((UserRegistered)(object)).getCustomer().getNumber(),message);
                break;
        }
    }

    public void createEmailNotificaton(String notifyingService, Object object,String message)
    {

    }

    public void createWhatsappNotification(String notifyingService, Object object,String message) {

    }

    public void sendSmsNotification(Long number, String message) {
        System.out.println("we are here with "+number+" : "+message);
    }

    public void sendEmailNotificaton(String email, String message) {

    }

    public void sendWhatsappNotification(Long number, String message) {

    }
}
