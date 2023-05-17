package com.spring.restapi.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilterController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue getsomebean() {
		Somebean somebean = new Somebean("value1","value2","value3");
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(somebean);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("val1");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue getsomebeanslist() {
		
		List<Somebean> list = Arrays.asList(
				new Somebean("value1","value2","value3"),
				new Somebean("value4","value5","value6")
				);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("val2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
}
