package com.gbr.nyan.web;

import com.gbr.nyan.support.HandlebarsRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
class HomeController {
    private final HandlebarsRenderer viewRenderer;

    @Autowired
    public HomeController(HandlebarsRenderer viewRenderer) {
        this.viewRenderer = viewRenderer;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = GET, produces = "text/html")
    public String root() throws IOException {
        Map<String, String> viewContext = new HashMap<>();
        viewContext.put("name", "World");

        return viewRenderer.render("/templates/home", viewContext);
    }
}
