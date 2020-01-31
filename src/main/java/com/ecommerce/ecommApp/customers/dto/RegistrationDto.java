package com.ecommerce.ecommApp.customers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegistrationDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private Long number;

    @NotNull
    @NotEmpty
    private Long whatsapp;
}
