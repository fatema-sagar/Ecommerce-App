package com.ecommerce.ecommApp.customers.controllers;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.customers.dto.LoginDto;
import com.ecommerce.ecommApp.customers.dto.RegistrationDto;
import com.ecommerce.ecommApp.customers.exceptions.EmailExistsException;
import com.ecommerce.ecommApp.customers.services.CustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody @Valid RegistrationDto registrationDetails){

        try {
            customerService.register(registrationDetails);
            return new ResponseEntity("User Registered Successfully.", HttpStatus.OK);
        }
        catch (EmailExistsException exception){
            return new ResponseEntity("Registration unsuccessful. "+exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDetails){

        try{
            CustomerDto customerDetails = customerService.loginCustomer(loginDetails);
            return new ResponseEntity<>(customerDetails,HttpStatus.OK);
        }
        catch (NotFoundException exception){
            return new ResponseEntity<Object>("Login Unsuccessful. "+exception.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/customer/{customerId}")
    ResponseEntity<?> getCustomerDetails(@PathVariable long customerId){

        try{
            CustomerDto customerDetails = customerService.getCustomerDetails(customerId);
            return new ResponseEntity<>(customerDetails,HttpStatus.OK);

        } catch (NotFoundException exception) {
            return new ResponseEntity<Object>("Login Unsuccessful. "+exception.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
