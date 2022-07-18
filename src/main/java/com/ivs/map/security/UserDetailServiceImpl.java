package com.ivs.map.security;


import com.ivs.map.Model.Account;
import com.ivs.map.Model.User;
import com.ivs.map.Repo.AccountRepo;
import com.ivs.map.Repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor


public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account acc = accountRepo.findById(username).get();
        Set<SimpleGrantedAuthority> setAuthorities = new HashSet<>();

        setAuthorities.add(new SimpleGrantedAuthority(acc.getRole()));


        return new CustomUserDetail(acc.getUsername(),
                acc.getPassword(),
                setAuthorities
        );


    }


}
