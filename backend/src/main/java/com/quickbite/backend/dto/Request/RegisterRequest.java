package com.quickbite.backend.dto.Request;


import com.quickbite.backend.entity.enums.Roles;
import lombok.Data;

import java.util.List;


@Data
public class RegisterRequest {

    private String name;
    private String  email;
    private Long phoneNumber;
    private String password;
    private Roles role;

}
