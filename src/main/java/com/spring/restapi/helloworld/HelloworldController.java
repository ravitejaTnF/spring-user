package com.spring.restapi.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldController {
	
	@Autowired
	private MessageSource messageSource;
	
	
	//@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	@GetMapping(path = "/hello-world")
	public String hello() {
		return "Hello world";
	}
	
	@GetMapping(path = "/hello-world-bean")
	public Name helloBean() {
		return new Name("Hello world bean");
	}
	
	@GetMapping(path = "/hello-world/path/user/{name}")
	public Name helloPathVariable(@PathVariable String name) {
		return new Name(name);
	}
	
	@GetMapping(path = "/hello-world/i18n")
	public String helloworldi18n() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("hello.world.message", null, "default message", locale);
	}
}
