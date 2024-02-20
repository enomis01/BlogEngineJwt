package com.example.BlogEngine.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityContext {

    public static String getCurrentUsername() {
        UserDetails user = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return user.getUsername();
    }

    public static boolean isCurrentUserAdmin() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getAuthorities().stream().map(authority -> (SimpleGrantedAuthority) authority)
                .findFirst()
                .get()
                .getAuthority()
                .equals("ADMIN");
    }
}
