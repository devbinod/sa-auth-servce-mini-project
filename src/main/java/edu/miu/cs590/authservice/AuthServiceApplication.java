package edu.miu.cs590.authservice;

import edu.miu.cs590.authservice.entity.User;
import edu.miu.cs590.authservice.enums.Role;
import edu.miu.cs590.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AuthServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public AuthServiceApplication(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        User user = new User();
        user.setUsername("binodpant.nep@gmail.com");
        user.setPassword(passwordEncoder.encode("binod"));
        user.setRole(Role.ADMIN);
        userRepository.saveAndFlush(user);
    }
}
