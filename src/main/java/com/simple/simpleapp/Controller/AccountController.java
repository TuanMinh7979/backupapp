package com.simple.simpleapp.Controller;

import com.simple.simpleapp.Model.Account;
import com.simple.simpleapp.Model.Customer;
import com.simple.simpleapp.Model.Driver;
import com.simple.simpleapp.Repo.AccountRepo;
import com.simple.simpleapp.Repo.CustomerRepo;
import com.simple.simpleapp.Repo.DriverRepo;
import com.simple.simpleapp.Repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DriverRepo driverRepo;
    @Autowired
    private CustomerRepo customerRepo;


    @GetMapping("/login")
    public String showLoginForm() {
        return "sign-in";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("account", new Account());
        return "sign-up";
    }

    @PostMapping("/register")
    public String createNewCustomer(Model model, Account account) {


        String message = "";
        if (userRepo.existsById(account.getPhone())) {
            message = "Phone number is existeds";

            model.addAttribute("message", message);
            return "sign-up";
        }
        if (accountRepo.existsById(account.getUsername())) {
            message = "Username is existed";

            model.addAttribute("message", message);
            return "sign-up";
        }


        if (account.getRole().equals("CUSTOMER")) {
            Customer customer = new Customer();
            customer.setFullname(account.getFullname());
            customer.setPhone(account.getPhone());
            customerRepo.save(customer);

//                try {
//					TimeUnit.SECONDS.sleep(3);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                return "redirect:/customer";
        } else {
            Driver driver = new Driver();
            driver.setAddress(account.getAddress());
            driver.setFullname(account.getFullname());
            driver.setPhone(account.getPhone());
            driverRepo.save(driver);
        }

        account.setRole("ROLE_" + account.getRole());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepo.save(account);

        return "redirect:/login";


    }


}
