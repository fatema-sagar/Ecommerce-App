package com.ecommerce.ecommApp.customers.controllers;

import com.ecommerce.ecommApp.customers.dto.RegistrationDto;
import com.ecommerce.ecommApp.customers.exceptions.EmailExistsException;
import com.ecommerce.ecommApp.customers.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationDto registrationDetails){

        try {
            customerService.register(registrationDetails);
            return new ResponseEntity("User Registered Successfully", HttpStatus.OK);
        }
        catch (EmailExistsException exception){
            return new ResponseEntity("Registration unsuccessful."+exception.getMessage(), HttpStatus.OK);
        }
    }


}
