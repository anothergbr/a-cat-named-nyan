package com.gbr.nyan.web;

import com.gbr.nyan.support.HandlebarsRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.gbr.nyan.web.support.Users.isUserLoggedIn;
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
        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("page-title", "A cat named Nyan");
        viewContext.put("rendering-home-page", true);
        viewContext.put("user-is-logged-in", isUserLoggedIn());

        return viewRenderer.render("/templates/home", viewContext);
    }
}
