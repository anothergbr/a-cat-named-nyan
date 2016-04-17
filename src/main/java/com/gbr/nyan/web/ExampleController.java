package com.gbr.nyan.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class ExampleController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "World");

        return "home";
    }
}
