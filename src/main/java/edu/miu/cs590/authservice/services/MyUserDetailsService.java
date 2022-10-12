package edu.miu.cs590.authservice.services;

import edu.miu.cs590.authservice.entity.User;
import edu.miu.cs590.authservice.exception.UsernameNotFoundException;
import edu.miu.cs590.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("User not found with " + username + " email");
        User myUser = user.get();
        return new org.springframework.security.core.userdetails.User(
                myUser.getUsername(),
                myUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + myUser.getRole().toString())));
    }
}
