package com.example.springtosalesforce.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.springtosalesforce.service.SFRestNetConnection;

public class CareerRepositorySFImpl implements CareerRepository {
	
	@Override
	public JSONObject careersById(String sfId) {
		
		System.out.println(sfId);
		
		SF_Rest_API sfRestAPI = new SF_Rest_API();
		sfRestAPI.setSfSOQLQuery("Select Id, Name, Company_Name__c, Position__c, Date_Started__c From Career__c where Id = '"+sfId+"'" );

		SFRestNetConnection sfConnection = new SFRestNetConnection();
		
		JSONObject result = sfConnection.query(sfRestAPI).getJSONObject(0);
		System.out.println("query result: "+ result);
		
		return result;
	}
	
	@Override
	public JSONObject updateCareer(Career career) {
		
		System.out.println("position: " + career.getPosition() + " Id: " + career.getSfId());
		
		SF_Rest_API sfRestAPI = new SF_Rest_API();
		sfRestAPI.setSfObjectType("Career__c");
		sfRestAPI.setSfObjectId(career.getSfId());
		
		JSONObject result = new JSONObject();
		boolean sfResults = false;
		JSONObject sfObject = new JSONObject();
		
		sfObject.put("Position__c", career.getPosition());
		
		sfRestAPI.setSfObjectJSON(sfObject);
		
		SFRestNetConnection sfConnection = new SFRestNetConnection();
		
		sfResults = sfConnection.updateObject(sfRestAPI);
		
		if(sfResults == false) {
			result.put("message", "Salesforce Update failed: " + sfObject);
		} else {
			result.put("message", "Salesforce Update successful");
		}
		
		return result;
	}

	@Override
	public JSONObject createCareer(Career career) {
		SF_Rest_API sfRestAPI = new SF_Rest_API();		
		sfRestAPI.setSfObjectType("Career__c");
		
		JSONObject result = new JSONObject();
		boolean sfResults = false;
		
        //create the JSON object containing the new details.
        JSONObject sfObject = new JSONObject();
        sfObject.put("Company_Name__c", career.getCompanyName());
        sfObject.put("Position__c", career.getPosition());
        sfObject.put("Name", career.getCareerName());
        sfObject.put("Date_Started__c", career.getDateStarted());
        
        sfRestAPI.setSfObjectJSON(sfObject);
		
		SFRestNetConnection sfConnection = new SFRestNetConnection();
		
		sfResults = sfConnection.createObject(sfRestAPI);
		
		if(sfResults == false) {
			result.put("message", "Salesforce Create failed: " + sfObject);
		} else {
			result.put("message", "Salesforce Create successful");
		}
		
		return result;
	}

	@Override
	public JSONObject deleteCareer(String sfId) {
		SF_Rest_API sfRestAPI = new SF_Rest_API();
		sfRestAPI.setSfObjectType("Career__c");
		sfRestAPI.setSfObjectId(sfId);
		
		JSONObject result = new JSONObject();
		boolean sfResults = false;
		
		SFRestNetConnection sfConnection = new SFRestNetConnection();
		sfResults = sfConnection.deleteObject(sfRestAPI);
		
		if(sfResults == false) {
			result.put("message", "Salesforce Delete failed: " + sfId);
		} else {
			result.put("message", "Salesforce Delete successful");
		}
		
		return result;
	}

}
