package com.gbr.nyan.web;

import com.gbr.nyan.support.HandlebarsRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static java.util.Collections.singletonMap;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
class LoginController {
    private final HandlebarsRenderer viewRenderer;

    @Autowired
    public LoginController(HandlebarsRenderer viewRenderer) {
        this.viewRenderer = viewRenderer;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = GET, produces = "text/html")
    public String login() throws IOException {
        return viewRenderer.render("/templates/login", singletonMap("page-title", "A cat named Nyan - login"));
    }
}
