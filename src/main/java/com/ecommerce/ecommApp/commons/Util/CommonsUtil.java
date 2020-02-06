package com.ecommerce.ecommApp.commons.Util;

import com.ecommerce.ecommApp.commons.NotificationProducer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonsUtil {

    private static final ObjectMapper objectMapper;
    private static final NotificationProducer notificationProducer;
    public static final String NOTIFICATION_USER_REGISTERED_SERVICE = "User Registered";
    public static final String NOTIFICATION_ORDER_PLACED_SERVICE = "Order Placed";
    public static final String NOTIFICATION_ORDER_STATUS_SERVICE = "Order Status";
    public static final String NOTIFICATION_ORDER_CANCELLED_SERVICE = "Order Cancelled";
    public static final String TWILIO_ASSIGNED_NUMBER = "twilio.number";
    public static final String SENDGRID_FROM_EMAIL = "sendgrid.email.from";
    public static String KAFKA_BOOTSTRAP_SERVERS = "kafka.bootstrap.server";

    public static String NOTIFICATION_REGISTERED_TOPIC = "notification.registered.topic";
    public static String NOTIFICATION_ORDER_PLACED_TOPIC = "notification.order.placed.topic";
    public static String NOTIFICATION_ORDER_STATUS_TOPIC = "notification.order.status.topic";
    public static String NOTIFICATION_ORDER_CANCEL_TOPIC = "notification.order.cancel.topic";


    static {
        notificationProducer = new NotificationProducer();
        objectMapper = new ObjectMapper();
    }

    public static NotificationProducer getNotificationProducer() {
        return notificationProducer;
    }

    public static ObjectMapper getObjectMapper() { return objectMapper; }
}
