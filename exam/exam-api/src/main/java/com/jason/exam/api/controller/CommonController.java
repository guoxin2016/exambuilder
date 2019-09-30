package com.jason.exam.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/pp")
    public String privacyPolicy() {
        return "pp";
    }

}
