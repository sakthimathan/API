package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.http.auth.InvalidCredentialsException;

import HTTPMethods.Methods;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Reusables {

	public  RequestSpecification reqSpec ;
	public  Response response;

	public void initResAssured() {
		reqSpec =RestAssured.given();

	}

	public void addHeaders(Map<String, String> map) {
		// type1
		/*
		 * RestAssured.given() .header("","") .header("","");
		 */

		// type2
		/*
		 * Header header1 = new Header("", ""); Header header2 = new Header("", "");
		 * 
		 * List<Header> list = new LinkedList<>(); list.add(header1); list.add(header2);
		 * 
		 * Headers headers = new Headers(list);
		 * 
		 * RestAssured.given() .headers(headers);
		 */

		// Type 3

		/*
		 * Map<String,String> map = new LinkedHashMap<>(); map.put("", ""); map.put("",
		 * "");
		 */
	reqSpec.headers(map);
	}

	public void addCookies(Map<String, String> map) {
//		reqSpec.cookie("","");
		reqSpec.cookies(map);
	}

	public void addQueryParam(Map<String, String> map) {
		reqSpec.queryParams(map);
	}

	public void addBasicAuth(String userName, String password) {
		reqSpec.auth().preemptive().basic(userName, password);
	}

	public void addOAuth2(String token) {
		reqSpec.auth().preemptive().oauth2(token);
	}

	public void addFileUpload(String fileDesc, String path) {
		File file = new File(path);
		reqSpec.multiPart(fileDesc, file);
	}

	public void addReqBody(Object body) {
		reqSpec.body(body);
	}

	public void addReqBodyFile(String filePath) {
		File file = new File(filePath);
		reqSpec.body(file);
	}

	public void addReqBodyAsString(String body) {

		reqSpec.body(body);
	}

	public Response reqType(Methods name) throws InvalidCredentialsException {

		switch (name) {
		case POST:
			response = reqSpec.log().all().post();
			break;
		case PUT:
			response = reqSpec.log().all().put();
			break;
		case PATCH:
			response = reqSpec.log().all().patch();
			break;
		case DELETE:
			response = reqSpec.log().all().delete();
			break;
		case GET:
			response = reqSpec.log().all().get();
			break;

		default:
			throw new InvalidCredentialsException("Invalid Method");
		}
		return response;
	}

	public String getDataFromProperty(String key) throws IOException {

		Properties prop = new Properties();
		File file = new File(System.getProperty("user.dir") + "\\Credentials.properties");
		FileInputStream stream = new FileInputStream(file);
		prop.load(stream);
		String value = prop.get(key).toString();
		return value;
	}

	public int statusCode() {
		int statusCode = response.getStatusCode();

		return statusCode;

	}

	public String statusLine() {
		String statusLine = response.getStatusLine();

		return statusLine;

	}

	public long responseTime() {
		long resTime = response.getTime();

		return resTime;

	}

	public List<String> responseHeadersKeys() {
		Headers headers = response.getHeaders();
		List<String> list = new ArrayList<>();
		for (Header header : headers) {
			String headerKey = header.getName();
			list.add(headerKey);
		}
		return list;
	}

	public List<String> responseHeadersValues() {
		Headers headers = response.getHeaders();
		List<String> list = new ArrayList<>();
		for (Header header : headers) {
			String headerValue = header.getValue();
			list.add(headerValue);
		}
		return list;
	}

	public String responseHeaderValue(String headerKey) {
		String header = response.getHeader(headerKey);
		return header;

	}

	public List<String> responseCookiesKeys() {
		Map<String, String> cookies = response.getCookies();
		Set<Entry<String, String>> entrySet = cookies.entrySet();
		List<String> list = new ArrayList<>();
		for (Entry<String, String> entry : entrySet) {
			String cookieskeys = entry.getKey();
			list.add(cookieskeys);

		}
		return list;
	}

	public List<String> responseCookiesValues() {
		Map<String, String> cookies = response.getCookies();
		Set<Entry<String, String>> entrySet = cookies.entrySet();
		List<String> list = new ArrayList<>();
		for (Entry<String, String> entry : entrySet) {
			String cookiesValues = entry.getValue();
			list.add(cookiesValues);

		}
		return list;
	}

	public String getCookie(String key) {
		String cookie = response.getCookie(key);
		return cookie;
	}

	public int getInt(String xpath) {
		JsonPath jsonPath = response.jsonPath();
		int num = jsonPath.getInt(xpath);
		return num;
	}

	public String getString(String xpath) {
		JsonPath jsonPath = response.jsonPath();
		String value = jsonPath.getString(xpath);
		return value;
	}

	public int getSize(String xpath) {
		JsonPath jsonPath = response.jsonPath();
		int size = jsonPath.getInt(xpath);
		return size;
	}

	public String getStringBody() {
		String resBody = response.getBody().asPrettyString();
		return resBody;
	}

	public Object getBody(Class<Object> name) {
		Object resBody = response.as(name);
		return resBody;
	}

	public boolean validateJsonSchema(String schemaPath) {
		File file = new File(schemaPath);
		response.then().body(JsonSchemaValidator.matchesJsonSchema(file));
		return true;

	}

}
