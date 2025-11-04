package org.semicolon.semicolonartworksystem.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.semicolon.semicolonartworksystem.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String path = request.getServletPath();

        if(path.startsWith("/api/v1/auth")) {
            log.info("Permitted to access this resource");
            filterChain.doFilter(request, response);


        }


        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
        }

        final String authToken = authHeader.substring(7);
        final String email = jwtUtil.extractEmail(authToken);

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if(jwtUtil.isTokenValid(authToken, userDetails)) {

                var claims = jwtUtil.extractAllClaims(authToken);
                var roles = claims.get("roles",  java.util.List.class);

                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

                if(roles != null){
                    for(Object role : roles){
                        log.debug(role.toString());
                        if(role instanceof Map<?,?> rolesObj){
                            Object authority = rolesObj.get("authority");
                            if(authority != null){
                                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+authority.toString()));
                            }
                        }

                    }
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }
        filterChain.doFilter(request, response);

    }
}
