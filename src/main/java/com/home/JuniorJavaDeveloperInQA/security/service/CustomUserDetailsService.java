package com.home.JuniorJavaDeveloperInQA.security.service;

import com.home.JuniorJavaDeveloperInQA.db.entity.MyUser;
import com.home.JuniorJavaDeveloperInQA.db.repository.MyUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    public CustomUserDetailsService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MyUser myUser= myUserRepository.findByLogin(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
        }
        return User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles("USER")
                .build();
    }
}
