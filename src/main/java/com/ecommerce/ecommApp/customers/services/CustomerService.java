package com.ecommerce.ecommApp.customers.services;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.commons.NotificationProducer;
import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.notification.UserRegistered;
import com.ecommerce.ecommApp.customers.dto.LoginDto;
import com.ecommerce.ecommApp.customers.dto.RegistrationDto;
import com.ecommerce.ecommApp.customers.exceptions.EmailExistsException;
import com.ecommerce.ecommApp.customers.models.Customer;
import com.ecommerce.ecommApp.customers.repository.CustomerRepository;
import com.ecommerce.ecommApp.commons.enums.NotificationType;
import com.ecommerce.ecommApp.customers.utils.CustomerUtil;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    NotificationProducer notificationProducer;
    CustomerUtil customerUtil = new CustomerUtil();

    public void register(RegistrationDto registrationDetails) throws EmailExistsException {

        if (emailExists(registrationDetails.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address: "
                            + registrationDetails.getEmail());
        }
        Customer customer = new Customer();
        customer.setName(registrationDetails.getName());
        customer.setEmail(registrationDetails.getEmail());
        customer.setNumber(registrationDetails.getNumber());
        customer.setPassword(passwordEncoder().encode(registrationDetails.getPassword()));
        customer.setWhatsapp(registrationDetails.getWhatsapp());
        customer.setGender(registrationDetails.getGender());
        customerRepository.save(customer);

        CustomerDto customerDto = customerUtil.convertToPojo(customer);
        sendRegistrationNotification(customerDto);
    }

    public CustomerDto loginCustomer(LoginDto loginDetails) throws NotFoundException {

        Optional<Customer> loggedInCustomer = customerRepository.findByEmail(loginDetails.getEmail());
        if (loggedInCustomer.isPresent() && passwordEncoder().matches(loginDetails.getPassword(),
                loggedInCustomer.get().getPassword())) {
            CustomerDto customerDetails = customerUtil.convertToPojo(loggedInCustomer.get());
            return customerDetails;
        } else {
            throw new NotFoundException("Customer Does not Exist");
        }
    }

    public CustomerDto getCustomerDetails(Long customerId) throws NotFoundException {
        Optional<Customer> loggedInCustomer = customerRepository.findById(customerId);
        if(loggedInCustomer.isPresent()){
            CustomerDto customerDetails = customerUtil.convertToPojo(loggedInCustomer.get());
            return customerDetails;
        } else{
            throw new NotFoundException("Wrong Customer Id");
        }
    }

    public CustomerDto updateCustomerDetails(CustomerDto customerDetails) {
        Customer customer = customerRepository.findById(customerDetails.getId()).get();
        if(null!=customer){

            customer.setEmail(customerDetails.getEmail());
            customer.setName(customerDetails.getName());
            customer.setGender(customerDetails.getGender());
            customer.setNumber(customerDetails.getNumber());
            customer.setWhatsapp(customerDetails.getWhatsapp());
            customerRepository.save(customer);
            return customerUtil.convertToPojo(customer);
        } else{
            throw new NoSuchElementException("Customer Not Found");
        }
    }

    private void sendRegistrationNotification(CustomerDto customerDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegistered userRegistered = new UserRegistered();
        userRegistered.setMode(new ArrayList<String>());
        userRegistered.getMode().add(NotificationType.Text_SMS.toString());
        userRegistered.getMode().add(NotificationType.EMAIL.toString());
        userRegistered.setCustomerDto(customerDto);
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
