package org.hyperskill.gradleapp.service;

import org.hyperskill.gradleapp.entity.User;
import org.hyperskill.gradleapp.security.UserDetailsImpl;
import org.hyperskill.gradleapp.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Not found: " + email);
        }
        return new UserDetailsImpl(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User getUser(String email){
        return userRepository.findByEmail(email);
    }
}
