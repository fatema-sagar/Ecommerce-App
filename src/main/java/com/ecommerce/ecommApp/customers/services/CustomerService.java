package com.ecommerce.ecommApp.customers.services;


import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.commons.NotificationProducer;
import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.pojo.customer.Customer;
import com.ecommerce.ecommApp.commons.pojo.notification.UserRegistered;
import com.ecommerce.ecommApp.customers.dto.RegistrationDto;
import com.ecommerce.ecommApp.customers.exceptions.EmailExistsException;
import com.ecommerce.ecommApp.customers.repository.CustomerRepository;
import com.ecommerce.ecommApp.commons.enums.NotificationType;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    NotificationProducer notificationProducer;

    public void register(RegistrationDto registrationDetails) throws EmailExistsException {

        if (emailExists(registrationDetails.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            + registrationDetails.getEmail());
        }

        Customer customer = new Customer();
        //String encodedPassword = passwordEncoder.encode(registrationDetails.getPassword());
        customer.setName(registrationDetails.getName());
        customer.setEmail(registrationDetails.getEmail());
        customer.setNumber(registrationDetails.getNumber());
        //customer.setPassword(encodedPassword);
        customer.setPassword(registrationDetails.getPassword());
        customer.setWhatsapp(registrationDetails.getWhatsapp());
        customer.setGender(registrationDetails.getGender());

        customerRepository.save(customer);

        ObjectMapper objectMapper = new ObjectMapper();

        UserRegistered userRegistered = new UserRegistered();
        userRegistered.setMode(new ArrayList<String>());
        userRegistered.getMode().add(NotificationType.Text_SMS.toString());
        userRegistered.getMode().add(NotificationType.EMAIL.toString());
        userRegistered.setCustomer(customer);
        notificationProducer = CommonsUtil.getNotificationProducer();
        try {
            notificationProducer.producerNotification(objectMapper.writeValueAsString(userRegistered),
                    EcommAppApplication.environment.getRequiredProperty(NotificationUtil.NOTIFICATION_REGISTERED_TOPIC));
        } catch (IOException ex) {

        }
    }

    private boolean emailExists(String email) {

        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return true;
        }
        return false;
    }

}
