package com.wissen.recruit.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackContoller {
	
	@GetMapping("/superadmin-message")
	public String test() {
		return "Admin service is down temporarily. Please check later.";
	}

}