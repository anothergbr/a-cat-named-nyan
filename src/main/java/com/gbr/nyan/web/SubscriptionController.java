package com.gbr.nyan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SubscriptionController {

    @ResponseBody
    @RequestMapping(value = "/subscription/create/notification", method = GET, consumes = "*", produces = "text/html")
    public String create() throws IOException {
        return "<html><body>1!!!</body></html>";
    }
}
