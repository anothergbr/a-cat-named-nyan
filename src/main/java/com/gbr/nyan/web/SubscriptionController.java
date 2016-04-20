package com.gbr.nyan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SubscriptionController {

    @ResponseBody
    @RequestMapping(value = "/subscription/create/notification", method = GET, consumes = "*", produces = "application/json")
    public FakeResponse create() throws IOException {
        return new FakeResponse("success");
    }

    private class FakeResponse {
        public final String status;

        public FakeResponse(String status) {
            this.status = status;
        }
    }

}
