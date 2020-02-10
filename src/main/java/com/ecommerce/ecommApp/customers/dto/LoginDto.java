package com.ecommerce.ecommApp.customers.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

    @NotEmpty
    @NotNull
    private String email;

    @NotNull
    @NotEmpty
    private String password;
}
