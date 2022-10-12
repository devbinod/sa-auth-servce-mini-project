package edu.miu.cs590.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/info")
    public String getInfo(){
        return "hello world";
    }
}
