package org.semicolon.semicolonartworksystem.dtos.requests;

import lombok.Data;
import org.semicolon.semicolonartworksystem.data.models.Role;

import java.util.Set;

@Data
public class SignUpRequest {
    private String userName;
    private String email;
    private String password;

}
