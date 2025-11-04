package org.semicolon.semicolonartworksystem.services;

import org.semicolon.semicolonartworksystem.data.models.User;
import org.semicolon.semicolonartworksystem.data.models.UserPrincipal;
import org.semicolon.semicolonartworksystem.data.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User foundUser =  userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return new UserPrincipal(foundUser);
    }
}
