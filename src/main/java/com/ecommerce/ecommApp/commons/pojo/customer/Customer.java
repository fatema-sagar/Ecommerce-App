package com.ecommerce.ecommApp.commons.pojo.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Component
@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false,unique = true)
    private Long uid;

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
