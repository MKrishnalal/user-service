package com.ncs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="greet")
public class Appcontroller {

	@GetMapping()
	public String  getGreeting(){
		return "Hello world";
	}
	
}
