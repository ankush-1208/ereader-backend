package com.ankush.readapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationRequest {

    @NotNull(message = "First Name cannot be empty")
    private String firstName;

    @NotNull(message = "Last Name cannot be empty")
    private String lastName;

    @NotNull(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;

}
