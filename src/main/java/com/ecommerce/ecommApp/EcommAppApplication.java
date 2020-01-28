package com.ecommerce.ecommApp;

import com.ecommerce.ecommApp.notifications.NotificationService;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class EcommAppApplication {

	@Autowired
	Environment environment;
	NotificationService notificationService;


	public static void main(String[] args) {
		SpringApplication.run(EcommAppApplication.class, args);

		EcommAppApplication context;
		context=new EcommAppApplication();
		context.startNotificationService();
	}

	public void startNotificationService()
	{
		String notificationTopic=environment.getProperty(NotificationUtil.NOTIFICATION_KAFKA_TOPIC);
		notificationService= new NotificationService(notificationTopic);
		notificationService.start();

	}

}
