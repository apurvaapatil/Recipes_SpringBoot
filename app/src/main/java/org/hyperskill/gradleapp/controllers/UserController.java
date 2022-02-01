package org.hyperskill.gradleapp.controllers;

import org.hyperskill.gradleapp.entity.User;
import org.hyperskill.gradleapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user){
        if(userDetailsServiceImpl.getUser(user.getEmail()) == null){
            user.setPassword(encoder.encode(user.getPassword()));
            userDetailsServiceImpl.saveUser(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
