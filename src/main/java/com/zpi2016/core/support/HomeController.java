package com.zpi2016.core.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by aman on 12.03.16.
 */

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "/index.html";
    }

}
