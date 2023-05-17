package com.spring.restapi.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
	
	@GetMapping(path = "/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("ravi teja");
	}
	
	@GetMapping(path = "/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("ravi","Teja"));
	}
	
	@GetMapping(path = "/person",params="version=1")
	public PersonV1 getPerson1() {
		return new PersonV1("person1");
	}
	
	@GetMapping(path = "/person",params="version=2")
	public PersonV2 getPerson2() {
		return new PersonV2(new Name("person2","person2"));
	}
}	
