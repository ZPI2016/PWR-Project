package com.zpi2016.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kuba on 26.02.16.
 */

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home(){
        return "index";
    }
}
