package com.apiautomation.bpi;

import io.restassured.response.Response;

import java.util.*;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidateBpi {

	    @Test
	    public void verifyBpiResponse() {
	        // Step 1: First I'll send the GET request to the endpoint mentioned
	        String endpoint = "https://api.coindesk.com/v1/bpi/currentprice.json";
	        Response response = RestAssured.given().get(endpoint);

	        // then Ensure the request was successful
	        Assert.assertEquals(response.statusCode(), 200, "Status code is not 200");

	        // then Parse the response body into a Map
	        Map<String, Object> responseBody = response.jsonPath().getMap("$");

	        // then I'll Extract the BPI object from the response
	        Map<String, Object> bpi = (Map<String, Object>) responseBody.get("bpi");
	        Assert.assertNotNull(bpi, "BPI object is missing from the response");

	        // Step 2a: Verify there are 3 BPIs: USD, GBP, and EUR
	        Assert.assertTrue(bpi.containsKey("USD"), "USD is missing from the BPI");
	        Assert.assertTrue(bpi.containsKey("GBP"), "GBP is missing from the BPI");
	        Assert.assertTrue(bpi.containsKey("EUR"), "EUR is missing from the BPI");

	        // Step 2b: Verify the GBP 'description' equals 'British Pound Sterling'
	        Map<String, Object> gbpDetails = (Map<String, Object>) bpi.get("GBP");
	        Assert.assertNotNull(gbpDetails, "GBP details are missing from the BPI");

	        String gbpDescription = (String) gbpDetails.get("description");
	        Assert.assertEquals(gbpDescription, "British Pound Sterling", "GBP description is incorrect");

	        // At last I'll validate Output success message for clarity
	        System.out.println("All BPI checks passed successfully.");
	    }
	}
