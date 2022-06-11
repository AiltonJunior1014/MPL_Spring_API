package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;


public class teste {

    
    public teste() {
    }
    
    @GetMapping("/index")
    public String test() {
        return"teste";
        
    }
}
