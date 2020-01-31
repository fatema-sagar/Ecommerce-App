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
@Data
@NoArgsConstructor
public class Customer
{
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false,unique = true)
    private Long id;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("email")
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @JsonProperty("number")
    @Column(name="number")
    private Long number;

    @JsonProperty("whatsapp")
    @Column(name="whatsapp")
    private Long whatsapp;

    @JsonProperty("gender")
    @Column(name="gender")
    private String gender;

}
