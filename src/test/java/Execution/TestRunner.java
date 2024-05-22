package Execution;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.auth.InvalidCredentialsException;
import org.testng.Assert;
import org.testng.annotations.Test;

import Endpoints.EndPoints;
import HTTPMethods.Methods;
import ReqBodyPOJO.GetAmazonPOJO;
import Utilities.Reusables;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestRunner extends Reusables {

	@Test
	public void url() throws IOException, InvalidCredentialsException {
		RestAssured.baseURI = EndPoints.POST;
//		RestAssured.basePath = "/repos/OWNER/REPO";
		initResAssured();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(getDataFromProperty("coffeePostHeaderKey1"), getDataFromProperty("coffeePostHeaderValue1"));
		map.put(getDataFromProperty("coffeePostHeaderKey2"), getDataFromProperty("coffeePostHeaderValue2"));
		addHeaders(map);

		addReqBodyFile(System.getProperty("user.dir") + "\\JsonReqBody\\Post_Req_Body_Coffee.json");
		
		reqType(Methods.POST);
		
		Assert.assertEquals(statusCode(), 200);

	}
	
	
	@Test
	private void getAmazon() {		
		
        String reqBody = "{\r\n"
				+ "  \"description\": \"API testing\",\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"name\": \"Areash\"\r\n"
				+ "}";
		
			RestAssured.baseURI= "https://www.amazon.in";
			RestAssured.basePath = "/s";
			
			Map<String , Object> header = new LinkedHashMap<String, Object>();
			header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n"
					+ "");
			header.put("Accept-Encoding", "gzip, deflate, br, zstd");
			header.put("Accept-Language", "en-US,en;q=0.9");
			
			Map<String , Object> query = new LinkedHashMap<String, Object>();
			query.put("crid","2U51K1NU5WK5F");
			query.put("i","aps");
			query.put("k","iPhone");
			query.put("ef","nb_sb_noss_1");
			query.put("sprefix","iphone%2Caps%2C238");
			query.put("url","search-alias%3Daps");
	
			
			Map<String , Object> cookies = new LinkedHashMap<String, Object>();
			cookies.put("session-id","259-4069925-4184908");
			cookies.put("i18n-prefs","INR");
			cookies.put("ubid-acbin","260-6591270-2013318");
			
			GetAmazonPOJO get= new GetAmazonPOJO("api testing", 5, "java");
			
			// request
			Response response = RestAssured.given()
			.headers(header)
			.queryParams(query)
			.cookies(cookies)
			.auth()
			.preemptive()
//			.basic("", "")
			.oauth2("")
//			.body(reqBody)
//			.body(System.getProperty("user.dir")+"\\JsonReqBody\\Post_Req_Body_Coffee.json");
			.body(get)
//			.get("https://www.amazon.in");
			.contentType("application/json")
			.sessionId("")
			.log()
			.all()
			.get();
		
			//response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getStatusLine().contains("OK"), "OK");
			long time = response.getTime();
			if (time<500) {
				Assert.assertTrue(true,"expected time");
			}
			
			Assert.assertEquals(response.getSessionId(), "");
			Assert.assertEquals(response.getContentType(), "");
			
			Headers headers = response.getHeaders();
			
			for (Header header2 : headers) {
				String headerKey=header2.getName();
				String headerValue=header2.getValue();
			}
			
			Assert.assertEquals(headers.get("Content-type"), "application/json");
			
			Map<String, String> cookies2 = response.getCookies();
			
			Set<Entry<String, String>> entrySet = cookies2.entrySet();
			for (Entry<String, String> entry : entrySet) {
				String cookiekeys = entry.getKey();
				String cookieValues = entry.getKey();
				
			}
			
			Assert.assertEquals(cookies2.get("session-id"), "xdfghjk");
			
			File file= new File(System.getProperty("user.dir")+"\\Schema\\Coffeee_Get_Res_Schema.json");
			
			response.then().body(JsonSchemaValidator.matchesJsonSchema(file));
			
			String asPrettyString = response.getBody().asPrettyString();
			System.out.println(asPrettyString);
		
			
//		JsonPath jsonPath = response.jsonPath();
//		jsonPath.getInt("")
//		jsonPath.getString("")
//		jsonPath.getInt("")
		
		File file1 = new File(System.getProperty("user.dir")+"\\JsonResBody\\resBody_Amazon.json");
		
		JsonPath jsonPath = new JsonPath(file1);
		int size = jsonPath.getInt("data.size()");
		System.out.println("size --> "+size);
		
		for (int i = 0; i <size ; i++) {
			
			String firstName = jsonPath.getString("data["+i+"].first_name");
			
			if(firstName.equalsIgnoreCase("Byron")) {
				String email = jsonPath.getString("data["+i+"].email");
				System.out.println("email --> "+email);
			}
			
		}
		

	}
	
	private void method() {
		String a = null;
		String b = "areash";
String name = a+b;
	}
}
