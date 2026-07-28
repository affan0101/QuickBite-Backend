package com.quickbite.backend.dto.response;

import com.quickbite.backend.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private Enum<Roles> role;

    private String message;

}
