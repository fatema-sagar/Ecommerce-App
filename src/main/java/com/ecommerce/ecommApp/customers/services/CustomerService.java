package com.ecommerce.ecommApp.customers.services;


import com.ecommerce.ecommApp.commons.pojo.customer.Customer;
import com.ecommerce.ecommApp.customers.dto.RegistrationDto;
import com.ecommerce.ecommApp.customers.exceptions.EmailExistsException;
import com.ecommerce.ecommApp.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public void register(RegistrationDto registrationDetails) throws EmailExistsException {


            if (emailExists(registrationDetails.getEmail())) {
                throw new EmailExistsException(
                        "There is an account with that email adress: "
                                + registrationDetails.getEmail());
            }

            Customer customer = new Customer();
            customer.setName(registrationDetails.getName());
            customer.setEmail(registrationDetails.getEmail());
            customer.setNumber(registrationDetails.getNumber());
            customer.setPassword(registrationDetails.getPassword());
            customer.setWhatsapp(registrationDetails.getWhatsapp());
            customer.setGender(registrationDetails.getGender());

            customerRepository.save(customer);

    }
        private boolean emailExists (String email){

            Optional<Customer> customer = customerRepository.findByEmail(email);
            if (customer.isPresent()) {
                return true;
            }
            return false;
        }

}
