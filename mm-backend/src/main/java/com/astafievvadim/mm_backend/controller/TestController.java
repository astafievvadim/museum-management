package com.astafievvadim.mm_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String basic() {
        return "The floor here is made out of floor ";
    }

    @GetMapping("/privet")
    public String priv() {
        return "Privet Stepa i Igor";
    }

    @GetMapping("/poka")
    public String pok() {
        return "Poka Stepa i Igor";
    }
}
