package com.quickbite.backend.dto.Request;


import lombok.Data;


@Data
public class RegisterRequest {

    private String name;
    private String  email;
    private Long phoneNumber;
    private String password;

}
