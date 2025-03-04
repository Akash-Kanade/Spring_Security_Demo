package com.security.service;

import com.security.entity.Users;
import com.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

   private final UserRepository repo;

    public CustomUserDetailService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = repo.findByUserName(username);
        if(Objects.isNull(user))
            throw new UsernameNotFoundException("Record not found with entered credentials !!!");

        return new CustomUserDetails(user.get());
    }
}
