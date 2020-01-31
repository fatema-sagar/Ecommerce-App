package com.ecommerce.ecommApp.customers.dto;

import com.ecommerce.ecommApp.customers.customAnnotations.PasswordMatches;
import com.ecommerce.ecommApp.customers.customAnnotations.ValidEmail;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter @PasswordMatches
public class RegistrationDto {
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotNull(message = "Please enter number")
    private Long number;

    private Long whatsapp;

    @NotNull
    @NotEmpty
    private String gender;
}
