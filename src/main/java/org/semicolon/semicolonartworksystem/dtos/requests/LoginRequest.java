package org.semicolon.semicolonartworksystem.dtos.requests;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
    private String email;
    private String password;
}
