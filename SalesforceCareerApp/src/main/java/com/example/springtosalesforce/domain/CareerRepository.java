package com.example.springtosalesforce.domain;

import org.json.JSONArray;
import org.json.JSONObject;

public interface CareerRepository {

	public JSONObject careersById(String sfId);
	
	public JSONObject updateCareer(Career career);
	
	public JSONObject createCareer(Career career);
	
	public JSONObject deleteCareer(String sfId);

}
