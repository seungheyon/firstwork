package com.example.firstwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {
    @GetMapping("/ss")
    public String test(Model model) {

        return "index2";
    }
}
