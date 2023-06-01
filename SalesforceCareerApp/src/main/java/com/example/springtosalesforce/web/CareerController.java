package com.example.springtosalesforce.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springtosalesforce.domain.Career;
import com.example.springtosalesforce.domain.CareerRepository;
import com.example.springtosalesforce.domain.CareerRepositorySFImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/careers")
public class CareerController {
	
	private CareerRepository repository = new CareerRepositorySFImpl();
	
	@PutMapping(consumes = {"application/json"})
	public String updateCareer(@RequestBody Career career) {
		
		JSONObject result = repository.updateCareer(career);
		
		return result.toString();
		
	}
	
	@PostMapping(consumes = {"application/json"})
	public String createCareer(@RequestBody Career career) {
		
		JSONObject result = repository.createCareer(career); 
		
		return result.toString();
		
	}
	
	@DeleteMapping(path="/{sfId}")
	public String deleteCareer(@PathVariable String sfId) {
		
		JSONObject result = repository.deleteCareer(sfId); 
		
		return result.toString();
		
	}
	
	@GetMapping(path="/{sfId}")
	public String careersBySFId(@PathVariable String sfId) {
		
		JSONObject result = repository.careersById(sfId); 
		
		return result.toString();
		
	}
	
	
	
}
