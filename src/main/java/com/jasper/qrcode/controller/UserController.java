package com.jasper.qrcode.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@GetMapping("/register")
	public String greeting() {
		return "Hello World";
	}
}
