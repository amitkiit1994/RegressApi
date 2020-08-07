package org.openWeatherMap.test;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openWeatherMap.TestUtil.TestUtil;
import org.openWeatherMap.base.TestBase;
import org.openWeatherMap.client.RestClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * Unit test for simple App.
 */
public class APITest extends TestBase {
	public static Logger log = LogManager.getLogger(APITest.class.getName());

	/**
	 * Rigorous Test :-)
	 * 
	 */
	TestBase testbase;
	String apiUrl;
	String serviceUrl;
	String url;
	String city;
//	String country;
	String appid;
	String page;
	CloseableHttpResponse httpResponse;
	RestClient restClient;
	int statusCode_200=Integer.valueOf(200);
	int statusCode_201=Integer.valueOf(201);
	int statusCode_204=Integer.valueOf(204);
	static String userId;

	@BeforeMethod
	public void setUp() throws URISyntaxException {
		testbase = new TestBase();
		appid=prop.getProperty("API_KEY");
		city=prop.getProperty("City");
//		country=prop.getProperty("Country");
		apiUrl = prop.getProperty("URL");
		page = prop.getProperty("Page");
		serviceUrl = prop.getProperty("serviceURL");
		url = apiUrl + serviceUrl;
//		System.out.println(url);
	}

	@Test(priority = 1)
	public void getTest() throws ParseException, IOException, URISyntaxException {
		restClient = new RestClient();
		URIBuilder builder = new URIBuilder(url);
	//	builder.setParameter("q", city).setParameter("appid", appid);
		builder.setParameter("page", page);
	//	System.out.println(builder.build());
		httpResponse = restClient.get(builder.build());

		// getStatus
		System.out.println("-----------------TEST FOR GET REQUEST--------------------------");
		if (TestBase.verifyStatus(httpResponse,statusCode_200 )) {
			log.info("Status code verified");
			System.out.println("Status code verified: "+statusCode_200);
		} else {
			fail();
			log.info("Status code verification failed");
			System.out.println("Status code verified");
		}
		// getResponse
		
		
		
		JSONObject jsonRepsonse=TestBase.getResponseString(httpResponse);
		//System.out.println("Json response: "+jsonRepsonse);
		String filePath=System.getProperty("user.home")+"\\weatherReportAPI.txt";
		TestUtil.createFile(filePath);
		TestBase.writeWeatherReportToFile(filePath, jsonRepsonse);
	
		// headers
		//System.out.println("Headers: "+TestBase.getHeaderHashMap(httpResponse));
		System.out.println("---------------------------------------------------------------");

	}
	
	@Test(priority = 2)
	public void postTest() throws URISyntaxException, IOException {
		restClient=new RestClient();
		URIBuilder builder= new URIBuilder(url);
		HashMap<String, String> headerMap=new HashMap<String, String>();
		headerMap.put("Content-type", "application/json");
		String am=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\org\\openWeatherMap\\inputFiles\\createUser_POST.json"), "UTF-8");
		//System.out.println(am);
		httpResponse=restClient.post(builder.build(), am, headerMap);
		System.out.println("-----------------TEST FOR POST REQUEST--------------------------");
		if (TestBase.verifyStatus(httpResponse,statusCode_201 )) {
			log.info("Status code verified");
			System.out.println("Status code verified: "+statusCode_201);
		} else {
			fail();
			log.info("Status code verification failed");
			System.out.println("Status code verification failed");
		}
		
		JSONObject jsonRepsonse=TestBase.getResponseString(httpResponse);
		//System.out.println("Json response: "+jsonRepsonse);
		System.out.println("ID: "+TestUtil.getValueByJPath(jsonRepsonse, "/id"));
		System.out.println("Name: "+TestUtil.getValueByJPath(jsonRepsonse, "/name"));
		System.out.println("Job: "+TestUtil.getValueByJPath(jsonRepsonse, "/job"));
		System.out.println("Created At: "+TestUtil.getValueByJPath(jsonRepsonse, "/createdAt"));
		userId=TestUtil.getValueByJPath(jsonRepsonse, "/id");
		System.out.println("----------------------------------------------------------------");
	}
	
	@Test(priority = 3)
	public void putTest() throws URISyntaxException, IOException {
		restClient=new RestClient();
		url=url+"/"+userId;
		URIBuilder builder = new URIBuilder(url);	
		HashMap<String, String> headerMap=new HashMap<String, String>();
		headerMap.put("Content-type", "application/json");
		String am=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\org\\openWeatherMap\\inputFiles\\updateUser_PUT.json"), "UTF-8");
		//System.out.println(builder.build());
		httpResponse=restClient.put(builder.build(), am, headerMap);
		System.out.println("-----------------TEST FOR PUT REQUEST--------------------------");
		if (TestBase.verifyStatus(httpResponse,statusCode_200 )) {
			log.info("Status code verified");
			System.out.println("Status code verified: "+statusCode_200);
		} else {
			fail();
			log.info("Status code verification failed");
			System.out.println("Status code verification failed");
		}
		
		JSONObject jsonRepsonse=TestBase.getResponseString(httpResponse);
		//System.out.println("Json response: "+jsonRepsonse);
		
		System.out.println("Name: "+TestUtil.getValueByJPath(jsonRepsonse, "/name"));
		System.out.println("Job: "+TestUtil.getValueByJPath(jsonRepsonse, "/job"));
		System.out.println("Updated At: "+TestUtil.getValueByJPath(jsonRepsonse, "/updatedAt"));
		System.out.println("----------------------------------------------------------------");
		
	}
	
	@Test(priority = 4)
	public void patchTest() throws URISyntaxException, IOException {
		restClient=new RestClient();
		url=url+"/"+userId;
		URIBuilder builder = new URIBuilder(url);	
		HashMap<String, String> headerMap=new HashMap<String, String>();
		headerMap.put("Content-type", "application/json");
		String am=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\org\\openWeatherMap\\inputFiles\\updateUser_PATCH.json"), "UTF-8");
		//System.out.println(builder.build());
		httpResponse=restClient.put(builder.build(), am, headerMap);
		System.out.println("-----------------TEST FOR PATCH REQUEST--------------------------");
		if (TestBase.verifyStatus(httpResponse,statusCode_200 )) {
			log.info("Status code verified");
			System.out.println("Status code verified: "+statusCode_200);
		} else {
			fail();
			log.info("Status code verification failed");
			System.out.println("Status code verification failed");
		}
		
		JSONObject jsonRepsonse=TestBase.getResponseString(httpResponse);
		//System.out.println("Json response: "+jsonRepsonse);
		
		System.out.println("Name: "+TestUtil.getValueByJPath(jsonRepsonse, "/name"));
		System.out.println("Job: "+TestUtil.getValueByJPath(jsonRepsonse, "/job"));
		System.out.println("Updated At: "+TestUtil.getValueByJPath(jsonRepsonse, "/updatedAt"));
		System.out.println("----------------------------------------------------------------");
		
	}
	
	@Test(priority = 5)
	public void getUserTest() throws ParseException, IOException, URISyntaxException {
		restClient=new RestClient();
		url=url+"/"+"2";
		URIBuilder builder = new URIBuilder(url);
		httpResponse = restClient.get(builder.build());

		// getStatus
		System.out.println("-----------------TEST FOR GET USER REQUEST--------------------------");
		if (TestBase.verifyStatus(httpResponse,statusCode_200 )) {
			log.info("Status code verified");
			System.out.println("Status code verified: "+statusCode_200);
		} else {
			fail();
			log.info("Status code verification failed");
			System.out.println("Status code verified");
		}
		JSONObject jsonRepsonse=TestBase.getResponseString(httpResponse);
		//System.out.println("Json response: "+jsonRepsonse);
		System.out.println("ID: "+TestUtil.getValueByJPath(jsonRepsonse, "/data/id"));
		System.out.println("Name: "+TestUtil.getValueByJPath(jsonRepsonse, "/data/first_name")+" "+TestUtil.getValueByJPath(jsonRepsonse, "/data/last_name"));
		System.out.println("Email: "+TestUtil.getValueByJPath(jsonRepsonse, "/data/email"));
		System.out.println("Company: "+TestUtil.getValueByJPath(jsonRepsonse, "/ad/company"));
		System.out.println("----------------------------------------------------------------");
	}
	
	@Test(priority = 6)
	public void deleteUserTest() throws ParseException, IOException, URISyntaxException {
		restClient=new RestClient();
		url=url+"/"+userId;
		URIBuilder builder = new URIBuilder(url);
		httpResponse = restClient.delete(builder.build());

		// getStatus
		System.out.println("-----------------TEST FOR DELETE USER REQUEST--------------------------");
		if (TestBase.verifyStatus(httpResponse,statusCode_204 )) {
			log.info("Status code verified");
			System.out.println("Status code verified: "+statusCode_204);
		} else {
			fail();
			log.info("Status code verification failed");
			System.out.println("Status code verified");
		}
		System.out.println("----------------------------------------------------------------");
	}
}
