package org.semicolon.semicolonartworksystem.data.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;

@Setter
@Getter
@Document(collection = "users")
public class User {
    @Id
    private String userId;
    private String userName;
    private String password;
    private String email;

    private Set<Role> role;
    private String token;

    private Cart cart;

}
