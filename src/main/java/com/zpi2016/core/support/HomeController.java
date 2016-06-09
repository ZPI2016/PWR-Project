package com.zpi2016.core.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by aman on 12.03.16.
 */

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/index.html";
    }

    @RequestMapping("/main")
    public String events(){
        return "/html/events.html";
    }

    @RequestMapping("/main/add-event")
    public String event(){
        return "/html/event.html";
    }

    @RequestMapping("/user")
    public String showMockedUser() {
        return "/html/user.html";
    }
}
