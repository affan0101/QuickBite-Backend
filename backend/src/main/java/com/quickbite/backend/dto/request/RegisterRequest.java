package com.quickbite.backend.dto.request;


import com.quickbite.backend.entity.enums.Roles;
import lombok.Data;


@Data
public class RegisterRequest {

    private String name;
    private String  email;
    private Long phoneNumber;
    private String password;
    private Roles role;

}
