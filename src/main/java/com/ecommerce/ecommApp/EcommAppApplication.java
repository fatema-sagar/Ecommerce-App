package com.ecommerce.ecommApp;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
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
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EcommAppApplication {

	private static final Logger log=LoggerFactory.getLogger(EcommAppApplication.class);

	public static ConfigurableApplicationContext context;
	public static Environment environment;

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		context = SpringApplication.run(EcommAppApplication.class, args);
		log.info("E-Comm application is started");
		environment = context.getBean(Environment.class);
		init();
		log.trace("starting Notification services");
//		startNotificationServices();
	}

	public static void startNotificationServices() {
		Thread userRegisteredThread = new UserRegisteredService(environment.getRequiredProperty(CommonsUtil.NOTIFICATION_REGISTERED_TOPIC));
		Thread orderPlacedThread = new OrderPlacedService(environment.getRequiredProperty(CommonsUtil.NOTIFICATION_ORDER_PLACED_TOPIC));
		Thread orderStatusThread = new OrderStatusService(environment.getRequiredProperty(CommonsUtil.NOTIFICATION_ORDER_STATUS_TOPIC));
		Thread orderCancelThread = new OrderCancelledService(environment.getRequiredProperty(CommonsUtil.NOTIFICATION_ORDER_CANCEL_TOPIC));
		userRegisteredThread.setName("User Registered");
		userRegisteredThread.start();
		orderPlacedThread.setName("Order Placed");
		orderPlacedThread.start();
		orderCancelThread.setName("Order Cancelled");
		orderCancelThread.start();
		orderStatusThread.setName("Order Status");
		orderStatusThread.start();
	}

	private static void init() {
		Twilio.init(environment.getRequiredProperty("twilio.sid"),environment.getRequiredProperty("twilio.access.token"));
	}
}
