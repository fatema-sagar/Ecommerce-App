package com.ecommerce.ecommApp.customers.controllers;

import com.ecommerce.ecommApp.commons.pojo.ResponseMessage;
import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.security.JwtTokenProvider;
import com.ecommerce.ecommApp.customers.dto.LoginDto;
import com.ecommerce.ecommApp.customers.dto.RegistrationDto;
import com.ecommerce.ecommApp.customers.exceptions.EmailExistsException;
import com.ecommerce.ecommApp.customers.models.CustomerAddress;
import com.ecommerce.ecommApp.customers.services.CustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Customer Controller to manage requests for customer module.
 */
@RestController
class CustomerController {

    private CustomerService customerService;
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomerController(CustomerService customerService, JwtTokenProvider tokenProvider){
        this.customerService = customerService;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Post method to register a customer
     * @param registrationDetails - Data entered by customer to register
     * @return customerDetails - registered customer's details
     * EmailExistsException - when already used email used for registration.
     */
    @PostMapping("/register")
    private ResponseEntity<Object> registerUser(@RequestBody @Valid RegistrationDto registrationDetails){

        try {
            CustomerDto customerDetails =  customerService.register(registrationDetails);
            return new ResponseEntity<>(customerDetails, HttpStatus.OK);
        }
        catch (EmailExistsException exception){
            return new ResponseEntity<>(new ResponseMessage("Registration unsuccessful. ","error"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Post request to login a customer
     * @param loginDetails - login credentials of customer
     * @return - {@link ResponseEntity}
     */
    @PostMapping("/login")
    private ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDetails){

        try{
            CustomerDto customerDetails = customerService.loginCustomer(loginDetails);
            return new ResponseEntity<>(customerDetails,HttpStatus.OK);
        }
        catch (NotFoundException exception){
            return new ResponseEntity<>(new ResponseMessage("Login Unsuccessful. ","error"),
                    HttpStatus.FORBIDDEN);
        }
    }

    /**
     * get request for obtaining customer details by its id.
     * @param customerId - registered customer's id.
     * @return - {@link ResponseEntity}
     */
    @GetMapping("/customer/{customerId}")
    private ResponseEntity<Object> getCustomerDetails(@PathVariable long customerId){
        try{
            CustomerDto customerDetails = customerService.getCustomerDetails(customerId);
            return new ResponseEntity<>(customerDetails,HttpStatus.OK);

        } catch (NotFoundException exception) {
            return new ResponseEntity<>(new ResponseMessage("User Id not found. ","error"),
                    HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Put request to update a customer's details
     * @param customerDetails - details to be updated
     * @return - {@link ResponseEntity}
     */
    @PutMapping("/customer")
    private ResponseEntity<Object> updateCustomerDetails(@RequestBody @Valid CustomerDto customerDetails){
        try{
            CustomerDto updatedCustomerDetails = customerService.updateCustomerDetails(customerDetails);
            return new ResponseEntity<>(updatedCustomerDetails,HttpStatus.OK);
        } catch(NoSuchElementException exception){
            return new ResponseEntity<>(new ResponseMessage("Update Unsuccessful. "+exception.getMessage(),"error"),
                    HttpStatus.FORBIDDEN);
        }
    }

    /**
     *
     * @param address -{@link CustomerAddress} customer address details
     * @param token - JWT authentication token
     * @return - Response entity of saved customer address
     */
    @PostMapping("/customer/address")
    private ResponseEntity<Object> addAddress(@RequestBody @Valid CustomerAddress address,
                                              @RequestHeader(name = "Authorization") String token){
        try{
            if(tokenProvider.validateToken(token)) {
                CustomerAddress newAddress = customerService.addCustomerAddress(address,token);
                return new ResponseEntity<>(newAddress, HttpStatus.OK);
            }
            else
                throw new AccessDeniedException("Invalid Token");
        }
        catch (AccessDeniedException exception) {
            return new ResponseEntity<>(new ResponseMessage("Cannot add Address. "+exception.getMessage(),"error"),
                    HttpStatus.FORBIDDEN);
        }
        catch(NoSuchElementException exception){
            return new ResponseEntity<>(new ResponseMessage("Unsuccessful. "+exception.getMessage(),"error"),
                    HttpStatus.FORBIDDEN);
        }
    }

    /**
     *
     * @param token - Jwt Authentication Token to manage logged in users
     * @return - List<{@link CustomerAddress} in the form of Response Entity.
     */
    @GetMapping("/customer/address")
    private ResponseEntity<Object> getAddresses(@RequestHeader(name = "Authorization") String token){
        try{
            if(tokenProvider.validateToken(token)){
                List<CustomerAddress> customerAddressList = customerService.getCustomerAddresses(token);
                return new ResponseEntity<>(customerAddressList, HttpStatus.OK);
            }
            else
                throw new AccessDeniedException("Invalid Token");
        }catch(Exception exception){
            return new ResponseEntity<>(new ResponseMessage("Unsuccessful. "+exception.getMessage(),"error"),
                    HttpStatus.FORBIDDEN);
        }
    }
}
