package org.example.bootthymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("data", "난 타임리프야");
        return "index";
    }
}
