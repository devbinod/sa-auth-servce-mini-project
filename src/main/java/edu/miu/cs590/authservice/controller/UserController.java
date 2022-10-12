package edu.miu.cs590.authservice.controller;

import edu.miu.cs590.authservice.dto.VerifyTokenDto;
import edu.miu.cs590.authservice.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    private MyUserDetailsService myUserDetailsService;

    public UserController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping("/api/info")
    public String getInfo(){
        return "hello world";
    }


    @PostMapping("/api/verity-token")
    public boolean verifyToken(@RequestBody VerifyTokenDto tokenDto){
        UserDetails user = myUserDetailsService.loadUserByUsername(tokenDto.getEmail());
        return user!=null && user.isAccountNonExpired() && user.isAccountNonLocked()
                && user.isEnabled() && user.isCredentialsNonExpired();
    }
}
