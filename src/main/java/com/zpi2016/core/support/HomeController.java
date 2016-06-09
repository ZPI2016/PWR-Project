package com.zpi2016.core.support;

import com.zpi2016.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by aman on 12.03.16.
 */

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        if (userService.getCurrentLoggedUser() == null)
            return "/index.html";
        else {
            return "redirect:/main";
        }
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
