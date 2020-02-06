package com.ecommerce.ecommApp;

import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.ecommerce.ecommApp.notifications.services.OrderCancelledService;
import com.ecommerce.ecommApp.notifications.services.OrderPlacedService;
import com.ecommerce.ecommApp.notifications.services.OrderStatusService;
import com.ecommerce.ecommApp.notifications.services.UserRegisteredService;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class EcommAppApplication {

	private static final Logger log=LoggerFactory.getLogger(EcommAppApplication.class);

	public static ConfigurableApplicationContext context;
	public static Environment environment;

	public static void main(String[] args) {
		context = SpringApplication.run(EcommAppApplication.class, args);
		log.info("E-Comm application is started");
		environment = context.getBean(Environment.class);
		log.trace("starting Notification services");
//		startNotificationServices();
	}

	public static void startNotificationServices() {
		Twilio.init(environment.getRequiredProperty("twilio.sid"),environment.getRequiredProperty("twilio.access.token"));
		Thread userRegisteredThread = new UserRegisteredService(environment.getRequiredProperty(NotificationUtil.NOTIFICATION_REGISTERED_TOPIC));
		Thread orderPlacedThread = new OrderPlacedService(environment.getRequiredProperty(NotificationUtil.NOTIFICATION_ORDER_PLACED_TOPIC));
		Thread orderStatusThread = new OrderStatusService(environment.getRequiredProperty(NotificationUtil.NOTIFICATION_ORDER_STATUS_TOPIC));
		Thread orderCancelThread = new OrderCancelledService(environment.getRequiredProperty(NotificationUtil.NOTIFICATION_ORDER_CANCEL_TOPIC));
		userRegisteredThread.setName("User Registered");
		userRegisteredThread.start();
		orderPlacedThread.setName("Order Placed");
		orderPlacedThread.start();
		orderCancelThread.setName("Order Cancelled");
		orderCancelThread.start();
		orderStatusThread.setName("Order Status");
		orderStatusThread.start();
	}
}
