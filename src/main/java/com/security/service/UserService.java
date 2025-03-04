package com.security.service;

import com.security.entity.Users;
import com.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public Users registerUser(Users user)
    {
        user.setPassword(
                encoder.encode(
                        user.getPassword()));
        return repository.save(user);

    }

    public String login(Users user) {
        Optional<Users> u = repository.findByUserName(user.getUserName());
        if(u.isPresent())
            if(user.getPassword().equals(u.get().getPassword()))
             return "User is authenticated with id: " + u.get().getId();
            else
                return "Invalid User Credentials !!!";
        else
            return "Invalid User Credentials !!!";

    }
}
