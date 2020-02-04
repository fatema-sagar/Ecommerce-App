package com.ecommerce.ecommApp.customers.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false,unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="number")
    private Long number;

    @Column(name="whatsapp")
    private Long whatsapp;

    @Column(name="gender")
    private String gender;

}
