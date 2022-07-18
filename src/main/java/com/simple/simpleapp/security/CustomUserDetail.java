package com.simple.simpleapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;



public class CustomUserDetail extends User {

    public CustomUserDetail(String username,
                            String password,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

    }


}
