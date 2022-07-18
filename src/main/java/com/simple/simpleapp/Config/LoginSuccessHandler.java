package com.simple.simpleapp.Config;


import com.simple.simpleapp.security.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        String redirectURL = request.getContextPath();
        String role = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()).get(0);
        System.out.println("MAIN ROLE :" + role);
        if (role.equals("ROLE_CUSTOMER")) {

            System.out.println("===REDIRECT TO CUSTOMER");
            redirectURL = "/customer";


        } else if (role.equals("ROLE_DRIVER")) {
            System.out.println("===REDIRECT TO DRIVER");
            redirectURL = "/driver";
        }

        response.sendRedirect(redirectURL);

    }
}


