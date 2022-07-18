package com.simple.simpleapp.Controller;

import com.simple.simpleapp.security.CustomUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;
@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        String message = "Hello Spring Boot + JSP";
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
            return "index";
        } else {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUserDetail userDetail = (CustomUserDetail) principal;

            String role = userDetail.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()).get(0);

            if (role.equals("ROLE_CUSTOMER")) {
                return  "redirect:/customer";
            } else if (role.equals("ROLE_DRIVER")) {
                return  "redirect:/driver";
            }else{
                return "redirect:/";
            }

        }
    }
    //Account Controller sẽ làm
//	@GetMapping("/sign-in")
//	public String signIn() {
//		return "sign-in";
//	}
//	@GetMapping("/sign-up")
//	public String signUp() {
//		return "sign-up";
//	}
    //Account Controller sẽ làm
    @GetMapping("/user-info")
    public String userInfo() {
        return "user-info";
    }
    @GetMapping("/driver-info")
    public String driverInfo() {
        return "driver-info";
    }
    /*
     * @GetMapping("/list-trip-init") public String listTripInit() { return
     * "list-trip-init"; }
     */
}