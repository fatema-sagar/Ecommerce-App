package com.ecommerce.ecommApp.customers.services;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.commons.NotificationProducer;
import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.pojo.JwtAuthentication;
import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.notification.UserRegistered;
import com.ecommerce.ecommApp.commons.security.*;
import com.ecommerce.ecommApp.customers.dto.LoginDto;
import com.ecommerce.ecommApp.customers.dto.RegistrationDto;
import com.ecommerce.ecommApp.customers.exceptions.EmailExistsException;
import com.ecommerce.ecommApp.customers.models.Customer;
import com.ecommerce.ecommApp.customers.repository.CustomerRepository;
import com.ecommerce.ecommApp.commons.enums.NotificationType;
import com.ecommerce.ecommApp.customers.utils.CustomerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private JwtTokenProvider jwtTokenProvider;
    private NotificationProducer notificationProducer;
    private CustomerUtil customerUtil;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerService(JwtTokenProvider jwtTokenProvider, CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.notificationProducer = CommonsUtil.getNotificationProducer();
        this.customerUtil = new CustomerUtil();
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager=authenticationManager;
    }


    public CustomerDto register(RegistrationDto regDetails) throws EmailExistsException {

        if (emailExists(regDetails.getEmail())) {
            throw new EmailExistsException(
                    CommonsUtil.EMAIL_EXISTS + regDetails.getEmail());
        }
        Customer customer = new Customer();
        customer.setName(regDetails.getName());
        customer.setEmail(regDetails.getEmail());
        customer.setNumber(regDetails.getNumber());
        customer.setPassword(passwordEncoder.encode(regDetails.getPassword()));
        customer.setWhatsapp(regDetails.getWhatsapp());
        customer.setGender(regDetails.getGender());
        customerRepository.save(customer);
        CustomerDto customerDto = customerUtil.convertToPojo(customer);
        sendRegistrationNotification(customerDto);
        return customerDto;
    }

    public CustomerDto loginCustomer(LoginDto loginDetails) throws NotFoundException {

        Optional<Customer> loggedInCustomer = customerRepository.findByEmail(loginDetails.getEmail());
        if (loggedInCustomer.isPresent() && passwordEncoder
                .matches(loginDetails.getPassword(), loggedInCustomer.get().getPassword())) {

            JwtAuthentication jwt = authenticateLogin(loginDetails);
            CustomerDto customer = customerUtil.convertToPojo(loggedInCustomer.get());
            customer.setJwt(jwt);
            return customer;
        } else {
            throw new NotFoundException(CommonsUtil.CUSTOMER_NOT_FOUND);
        }
    }

    public CustomerDto getCustomerDetails(Long customerId) throws NotFoundException {
        Optional<Customer> loggedInCustomer = customerRepository.findById(customerId);
        if (loggedInCustomer.isPresent()) {
            return customerUtil.convertToPojo(loggedInCustomer.get());
        } else {
            throw new NotFoundException(CommonsUtil.WRONG_CUSTOMER_ID);
        }
    }

    public CustomerDto updateCustomerDetails(CustomerDto customerDetails) {
        Optional<Customer> registeredCustomer = customerRepository.findById(customerDetails.getId());
        if (registeredCustomer.isPresent()) {

            Customer customer = registeredCustomer.get();
            if (customerUtil.isStringFieldValid(customerDetails.getEmail()))
                customer.setEmail(customerDetails.getEmail());
            if (customerUtil.isStringFieldValid(customerDetails.getName()))
                customer.setName(customerDetails.getName());
            if (customerUtil.isStringFieldValid(customerDetails.getGender()))
                customer.setGender(customerDetails.getGender());
            if (customerUtil.isPhoneNumberValid(customerDetails.getNumber().toString()))
                customer.setNumber(customerDetails.getNumber());
            if (customerUtil.isPhoneNumberValid(customerDetails.getWhatsapp().toString()))
                customer.setWhatsapp(customerDetails.getWhatsapp());

            customerRepository.save(customer);
            return customerUtil.convertToPojo(customer);
        } else {
            throw new NoSuchElementException(CommonsUtil.CUSTOMER_NOT_FOUND);
        }
    }

    private void sendRegistrationNotification(CustomerDto customerDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegistered userRegistered = new UserRegistered();
        userRegistered.setMode(new ArrayList<>());
        userRegistered.getMode().add(NotificationType.Text_SMS.toString());
        userRegistered.getMode().add(NotificationType.EMAIL.toString());
        userRegistered.setCustomerDto(customerDto);
        notificationProducer = CommonsUtil.getNotificationProducer();
        try {
            notificationProducer.producerNotification(objectMapper.writeValueAsString(userRegistered),
                    EcommAppApplication.environment.getRequiredProperty(CommonsUtil.NOTIFICATION_REGISTERED_TOPIC));
        } catch (IOException ignored) {
        }
    }

    private boolean emailExists(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        return customer.isPresent();
    }

    private JwtAuthentication authenticateLogin(LoginDto loginDetails){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDetails.getEmail(),
                        loginDetails.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return new JwtAuthentication(jwt);
    }

}
