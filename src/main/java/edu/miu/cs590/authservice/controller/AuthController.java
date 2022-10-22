package edu.miu.cs590.authservice.controller;


import edu.miu.cs590.authservice.config.jwt.JwtTokenUtil;
import edu.miu.cs590.authservice.dto.LoginDto;
import edu.miu.cs590.authservice.dto.LoginResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/v1/auth")
public class AuthController {


    static private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ResponseEntity<?> health1() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        return ResponseEntity.ok(response);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) {


        try {

            Authentication authentication = authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            String token = jwtTokenUtil.generateToken(user);
            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .authorities(user.getAuthorities())
                    .accountNonExpired(user.isAccountNonExpired())
                    .accountNonLocked(user.isAccountNonLocked())
                    .credentialsNonExpired(user.isCredentialsNonExpired())
                    .username(user.getUsername())
                    .enabled(user.isEnabled())
                    .token(token).build();

            return ResponseEntity.ok().body(loginResponseDto);

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ex.getMessage());
        }

    }
}
