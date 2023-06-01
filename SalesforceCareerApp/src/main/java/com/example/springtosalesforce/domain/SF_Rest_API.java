package com.example.springtosalesforce.domain;

import java.util.ResourceBundle;

import org.json.JSONObject;

public class SF_Rest_API {
	
	
	private String sfURL;
	private String sfGrantService;
	private String sfRestEndPoint;
	private String sfClientId;
	private String sfClientSecret;
	private String sfUser;
	private String sfPassword;
	private String sfAPIVersion;
	
	private String sfSOQLQuery;
	private String sfObjectType;
	private JSONObject sfObjectJSON;
	private String sfObjectId;
	
	public SF_Rest_API() {
		ResourceBundle rb = ResourceBundle.getBundle("salesforce");
		sfURL = rb.getString("sfURL");
		sfGrantService = rb.getString("sfGrantService");
		sfRestEndPoint = rb.getString("sfRestEndPoint");
		sfClientId = rb.getString("sfClientId");
		sfClientSecret = rb.getString("sfClientSecret");
		sfUser = rb.getString("sfUser");
		sfPassword = rb.getString("sfPassword");
		sfAPIVersion = rb.getString("sfAPIVersion");
		
	}

	public String getSfURL() {
		return sfURL;
	}

	public void setSfURL(String sfURL) {
		this.sfURL = sfURL;
	}

	public String getSfGrantService() {
		return sfGrantService;
	}

	public void setSfGrantService(String sfGrantService) {
		this.sfGrantService = sfGrantService;
	}

	public String getSfRestEndPoint() {
		return sfRestEndPoint;
	}

	public void setSfRestEndPoint(String sfRestEndPoint) {
		this.sfRestEndPoint = sfRestEndPoint;
	}

	public String getSfClientId() {
		return sfClientId;
	}

	public void setSfClientId(String sfClientId) {
		this.sfClientId = sfClientId;
	}

	public String getSfClientSecret() {
		return sfClientSecret;
	}

	public void setSfClientSecret(String sfClientSecret) {
		this.sfClientSecret = sfClientSecret;
	}

	public String getSfUser() {
		return sfUser;
	}

	public void setSfUser(String sfUser) {
		this.sfUser = sfUser;
	}

	public String getSfPassword() {
		return sfPassword;
	}

	public void setSfPassword(String sfPassword) {
		this.sfPassword = sfPassword;
	}

	public String getSfAPIVersion() {
		return sfAPIVersion;
	}

	public void setSfAPIVersion(String sfAPIVersion) {
		this.sfAPIVersion = sfAPIVersion;
	}

	public String getSfSOQLQuery() {
		return sfSOQLQuery;
	}

	public void setSfSOQLQuery(String sfSOQLQuery) {
		this.sfSOQLQuery = sfSOQLQuery;
	}

	public String getSfObjectType() {
		return sfObjectType;
	}

	public void setSfObjectType(String sfObjectType) {
		this.sfObjectType = sfObjectType;
	}

	public JSONObject getSfObjectJSON() {
		return sfObjectJSON;
	}

	public void setSfObjectJSON(JSONObject sfObjectJSON) {
		this.sfObjectJSON = sfObjectJSON;
	}

	public String getSfObjectId() {
		return sfObjectId;
	}

	public void setSfObjectId(String sfObjectId) {
		this.sfObjectId = sfObjectId;
	}
	
	

}
