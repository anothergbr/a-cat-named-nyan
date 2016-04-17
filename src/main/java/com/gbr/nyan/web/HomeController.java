package com.gbr.nyan.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController {

    @RequestMapping(value = "/", produces = "text/html")
    public String index(Model model) {
        model.addAttribute("name", "World");

        return "home";
    }
}
