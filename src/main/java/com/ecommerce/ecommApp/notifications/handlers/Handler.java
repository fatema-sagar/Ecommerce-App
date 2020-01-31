package com.ecommerce.ecommApp.notifications.handlers;

import java.util.List;

public interface Handler
{
    public void sendNotification(String notifyingService, List<String> modes, Object object, String message);
}
