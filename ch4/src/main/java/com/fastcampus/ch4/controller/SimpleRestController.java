package com.fastcampus.ch4.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class SimpleRestController {
    
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}

}