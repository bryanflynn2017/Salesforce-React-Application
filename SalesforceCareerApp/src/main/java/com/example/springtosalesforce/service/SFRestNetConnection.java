package com.example.springtosalesforce.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.springtosalesforce.domain.SF_Rest_API;

public class SFRestNetConnection {
	
	private int connectionTimeoutMillis = 120000;
	private final String SSLProtocols = "TLSv1.2";
	private String accessToken;
	private String baseUri; //https://curious-moose-bnlix6-dev-ed.lightning.force.com/services/data/v32.0
	
	public SFRestNetConnection() {
		
	}

	public void getNewAccessToken(SF_Rest_API sfRestAPI) {
		try {
			
			SSLContext sc = SSLContext.getInstance(SSLProtocols);
			sc.init(null, null, new java.security.SecureRandom());
			
			HashMap<String, String> nvps = new HashMap<String, String>();
			nvps.put("grant_type", "password");
			nvps.put("client_id", sfRestAPI.getSfClientId());
			nvps.put("client_secret", sfRestAPI.getSfClientSecret());
			nvps.put("username", sfRestAPI.getSfUser());
			nvps.put("password", sfRestAPI.getSfPassword());
			
			byte[] postDataBytes = getQueryString(nvps).getBytes("UTF-8");
			
			 URL url = new URL(sfRestAPI.getSfURL() + sfRestAPI.getSfGrantService());
			 
			 System.out.println("Web Call getAccessToken to: " + url.toString());
			 
			HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();

			 httpConn.setConnectTimeout(connectionTimeoutMillis);
			  httpConn.setReadTimeout(connectionTimeoutMillis);
			
			httpConn.setSSLSocketFactory(sc.getSocketFactory());      
		        
	        httpConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	        httpConn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        httpConn.setRequestMethod("POST");
	        httpConn.setDoOutput(true);
	        httpConn.setDoInput(true);
	        httpConn.getOutputStream().write(postDataBytes);
		
	        InputStreamReader isr;
	        try {
	            isr = new InputStreamReader(httpConn.getInputStream(),"UTF-8");
	            }
	        catch(IOException e) {
	            isr = new InputStreamReader(httpConn.getErrorStream(),"UTF-8");
	            }
	        BufferedReader in = new BufferedReader(isr);

	        String inputLine;
	        StringBuilder returnJson = new StringBuilder();
	        
	        while ((inputLine = in.readLine()) != null){
	            returnJson.append(inputLine);
	        }
	        
	        in.close();
	        
	        int statusCode = httpConn.getResponseCode();
	        JSONObject jsonObject = new JSONObject();
	        String loginInstanceUrl = null;
	       
	        if (statusCode == 200){
	        	jsonObject = new JSONObject(returnJson.toString());
	        	accessToken = jsonObject.getString("access_token");
	        	loginInstanceUrl = jsonObject.getString("instance_url");
	        	
		        baseUri = loginInstanceUrl + sfRestAPI.getSfRestEndPoint() + sfRestAPI.getSfAPIVersion();

		        System.out.println("statusCode: " + statusCode);
		        System.out.println("Successful login");
		        System.out.println("instance URL: "+loginInstanceUrl);
		        System.out.println("access token/session ID: "+ accessToken);
		        System.out.println("baseURI: "+ baseUri);

	        } else {
	        	System.out.println("statusCode" + statusCode);
	        	System.out.println("return: " + returnJson.toString());
	        }

		}catch (Exception ex){
			 
		}

	}

	public JSONArray query(SF_Rest_API sfRestAPI) {
		
		getNewAccessToken(sfRestAPI);
		
		JSONObject jObj = new JSONObject();
		
		try {
			
			String uri = baseUri + "/query/?q="+URLEncoder.encode(sfRestAPI.getSfSOQLQuery(),StandardCharsets.UTF_8);
			
			System.out.println("Web Call querySF to: " + uri.toString());

			CloseableHttpClient httpclient = HttpClients.createDefault();

		    HttpGet get = new HttpGet(uri);
		    	get.setHeader("grant_type", "authorization_code");	
		    	get.setHeader("Authorization", "Bearer " + accessToken);
			  
			CloseableHttpResponse response = httpclient.execute(get);
		
			int statusCode = response.getStatusLine().getStatusCode();
			  
			if (statusCode == 200){
				String ret2 = EntityUtils.toString(response.getEntity(), "UTF-8");
				jObj = new JSONObject(ret2);
				System.out.println("jObj: " + jObj);
				JSONArray j = jObj.getJSONArray("records");

				return j;
			} else {
	            System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
	            System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
	            System.exit(-1);
			}
			 
			response.close();
			  
			}catch (Exception ex){
				ex.printStackTrace();
			}
		return null;
	}

	public boolean updateObject(SF_Rest_API sfRestAPI) {
		
		boolean result = false;
		getNewAccessToken(sfRestAPI);
			
		try {
			SSLContext sc = SSLContext.getInstance(SSLProtocols);
			sc.init(null, null, new java.security.SecureRandom());
			
			URL uri = new URL(baseUri + "/sobjects/" + sfRestAPI.getSfObjectType() + "/" + sfRestAPI.getSfObjectId() + "?_HttpMethod=PATCH"); 
				
			System.out.println("Web Call updateJSON to: " + uri.toString());
				
			HttpsURLConnection httpConn = (HttpsURLConnection) uri.openConnection();

			httpConn.setConnectTimeout(connectionTimeoutMillis);
			httpConn.setReadTimeout(connectionTimeoutMillis);
			 
			httpConn.setSSLSocketFactory(sc.getSocketFactory());      
		        
	        httpConn.setRequestProperty("Content-Type","application/json");
	        httpConn.setRequestProperty("grant_type", "authorization_code");
	        httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
	        httpConn.setRequestMethod("POST");
	        httpConn.setDoOutput(true);
	        httpConn.setDoInput(true);
		
	       
	        OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream(), StandardCharsets.UTF_8);
	        	out.write(sfRestAPI.getSfObjectJSON().toString());
	        	out.flush();
	        	out.close();

	        InputStreamReader isr;
	        try {
	            isr = new InputStreamReader(httpConn.getInputStream(),"UTF-8");
	            }
	        catch(IOException e) {
	            isr = new InputStreamReader(httpConn.getErrorStream(),"UTF-8");
	            }
	        BufferedReader in = new BufferedReader(isr);

	        String inputLine;
	        StringBuilder returnJson = new StringBuilder();
	        
	        while ((inputLine = in.readLine()) != null){
	            returnJson.append(inputLine);
	        }
	        
	        in.close();
	        
	        int statusCode = httpConn.getResponseCode();
		     
	        if (statusCode == 204) {
                System.out.println("Updated the " + sfRestAPI.getSfObjectType() + " successfully.");
                result = true;
            } else {
                System.out.println(sfRestAPI.getSfObjectType() + " update NOT successfully. Status code is " + statusCode);
            }
	        
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		
		return result;
	}

	public boolean createObject(SF_Rest_API sfRestAPI) {
		
		boolean result = false;
		getNewAccessToken(sfRestAPI);
			
		try {
			SSLContext sc = SSLContext.getInstance(SSLProtocols);
			sc.init(null, null, new java.security.SecureRandom());
			
			URL uri = new URL(baseUri + "/sobjects/" + sfRestAPI.getSfObjectType() + "/");
				
			System.out.println("Web Call createJSON to: " + uri.toString());
					 
			HttpsURLConnection httpConn = (HttpsURLConnection) uri.openConnection();

			httpConn.setConnectTimeout(connectionTimeoutMillis);
		 	httpConn.setReadTimeout(connectionTimeoutMillis);
			 
			httpConn.setSSLSocketFactory(sc.getSocketFactory());      
		        
	        httpConn.setRequestProperty("Content-Type","application/json");
	        httpConn.setRequestProperty("grant_type", "authorization_code");
	        httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
	        httpConn.setRequestMethod("POST");
	        httpConn.setDoOutput(true);
	        httpConn.setDoInput(true);
			
	        OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream(), StandardCharsets.UTF_8);
	        	out.write(sfRestAPI.getSfObjectJSON().toString());
	        	out.flush();
	        	out.close();

	        InputStreamReader isr;
	        try {
	            isr = new InputStreamReader(httpConn.getInputStream(),"UTF-8");
	            }
	        catch(IOException e) {
	            isr = new InputStreamReader(httpConn.getErrorStream(),"UTF-8");
	            }
	        BufferedReader in = new BufferedReader(isr);

	        String inputLine;
	        StringBuilder returnJson = new StringBuilder();
	        
	        while ((inputLine = in.readLine()) != null){
	            returnJson.append(inputLine);
	        }
	        
	        in.close();
	        
	        int statusCode = httpConn.getResponseCode();
	            
	        if (statusCode == 201){
	        	//record created
                JSONObject json = new JSONObject(returnJson.toString());

                String id = json.getString("id");
                System.out.println("New " + sfRestAPI.getSfObjectType() + " id from response: " + id);
                result = true;
	        }
	        else if (statusCode == 204){
	        	//record updated
	        	System.out.println("Record updated.");
	        } else {
	        	System.out.println("Insertion unsuccessful. Status code returned is " + statusCode);
	        }
	        
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		return result;
	}

	public boolean deleteObject(SF_Rest_API sfRestAPI) {
		
		boolean result = false;
		getNewAccessToken(sfRestAPI);

		try {
			SSLContext sc = SSLContext.getInstance(SSLProtocols);
			sc.init(null, null, new java.security.SecureRandom());
			
			URL uri = new URL(baseUri + "/sobjects/" + sfRestAPI.getSfObjectType() + "/" + sfRestAPI.getSfObjectId() + "?_HttpMethod=DELETE");  
			
			System.out.println("Web Call delete to : " + uri.toString());
			
			HttpsURLConnection httpConn = (HttpsURLConnection) uri.openConnection();
			
			httpConn.setConnectTimeout(connectionTimeoutMillis);
			httpConn.setReadTimeout(connectionTimeoutMillis);
			 
			httpConn.setSSLSocketFactory(sc.getSocketFactory());      
		        
	        httpConn.setRequestProperty("grant_type", "authorization_code");
	        httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
	        httpConn.setRequestMethod("POST");
	        httpConn.setDoOutput(true);
	        httpConn.setDoInput(true);

	        InputStreamReader isr;
	        try {
	            isr = new InputStreamReader(httpConn.getInputStream(),"UTF-8");
	            }
	        catch(IOException e) {
	            isr = new InputStreamReader(httpConn.getErrorStream(),"UTF-8");
	            }
	        BufferedReader in = new BufferedReader(isr);

	        String inputLine;
	        StringBuilder returnJson = new StringBuilder();
	        
	        while ((inputLine = in.readLine()) != null){
	            returnJson.append(inputLine);
	        }
	        
	        in.close();
	        
	        int statusCode = httpConn.getResponseCode();
		       
            if (statusCode == 204) {
                System.out.println("Deleted the " + sfRestAPI.getSfObjectType() + " successfully.");
                result = true;
            } else {
                System.out.println(sfRestAPI.getSfObjectType() + " delete NOT successful. Status code is " + statusCode);
            }
	        
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		return result;
	}
	
	//formats querystring parms
	private String getQueryString(HashMap<String, String> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (String keyName : params.keySet())
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(keyName, "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(params.get(keyName), "UTF-8"));
	    }
	    System.out.println("getQueryString: " + result.toString());
	    return result.toString();
	}

}
