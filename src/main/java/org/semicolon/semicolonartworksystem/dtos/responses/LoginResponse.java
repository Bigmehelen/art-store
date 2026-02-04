package org.semicolon.semicolonartworksystem.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String token;
    public boolean isAdmin;
    public boolean isBuyer;

}
