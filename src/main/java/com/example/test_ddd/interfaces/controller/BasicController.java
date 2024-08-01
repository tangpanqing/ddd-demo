package com.example.test_ddd.interfaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicController {
    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "hello world";
    }
}
