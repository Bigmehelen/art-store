package org.semicolon.semicolonartworksystem.dtos.responses;

import lombok.Data;
@Data
public class LoginResponse {
    private String userId;
    private String email;
    private String token;
}
