package com.zpi2016.controller;

import java.util.Date;

import com.zpi2016.controller.util.MyRestResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Model model) {
		model.addAttribute("time", new Date());
		model.addAttribute("message", this.message);
		return "welcome";
	}

	@RequestMapping("/foo")
	public String foo() {
		throw new RuntimeException("Foo");
	}
	
	@RequestMapping("/rest-foo")
	public @ResponseBody MyRestResponse handleRequest() {
		return new MyRestResponse("Some data I want to send back to the client.");
	}
}
