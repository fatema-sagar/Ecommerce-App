package com.ecommerce.ecommApp.commons.Util;

import com.ecommerce.ecommApp.commons.NotificationProducer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class CommonsUtil {

    private static final NotificationProducer notificationProducer;

    static {
        notificationProducer = new NotificationProducer();
    }

    public static NotificationProducer getNotificationProducer() {
        return notificationProducer;
    }

}
