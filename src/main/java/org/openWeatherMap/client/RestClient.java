package org.openWeatherMap.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;



public class RestClient {

	// GET method
	public CloseableHttpResponse get(URI url) throws IOException, URISyntaxException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); // http get request
		CloseableHttpResponse httpResponse = null;
		try {
		
			httpResponse = httpClient.execute(httpget); // hit the GET url and get response
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return httpResponse;
	}
	
	//POST Method
	 public CloseableHttpResponse post(URI url,String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 HttpPost httpPost=new HttpPost(url);	//http post reqyest
		 CloseableHttpResponse httpResponse = null;
		 httpPost.setEntity(new StringEntity(entityString));   //for payload
		 for (Map.Entry<String, String> entry: headerMap.entrySet()) {
			 httpPost.addHeader(entry.getKey(), entry.getValue());
		 }
		 httpResponse= httpClient.execute(httpPost);	//post execution
		 return httpResponse;
	 }
	 
	 //PUT Method
	 public CloseableHttpResponse put(URI url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 HttpPut httpPut= new HttpPut(url);      //Http put request
		 CloseableHttpResponse httpResponse = null;
		 httpPut.setEntity(new StringEntity(entityString));   //for payload
		 for (Map.Entry<String, String> entry: headerMap.entrySet()) {
			 httpPut.addHeader(entry.getKey(), entry.getValue());
		 }
		 httpResponse= httpClient.execute(httpPut);	//put execution
		 return httpResponse;
	 }
	 
	 //PATCH Method
	 public CloseableHttpResponse patch(URI url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 HttpPatch httpPut= new HttpPatch(url);      //Http patch request
		 CloseableHttpResponse httpResponse = null;
		 httpPut.setEntity(new StringEntity(entityString));   //for payload
		 for (Map.Entry<String, String> entry: headerMap.entrySet()) {
			 httpPut.addHeader(entry.getKey(), entry.getValue());
		 }
		 httpResponse= httpClient.execute(httpPut);	//put execution
		 return httpResponse;
	 }
	 
	 //Delete Method
	 public CloseableHttpResponse delete(URI url) throws ClientProtocolException, IOException {
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 HttpDelete httpPut= new HttpDelete(url);      //Http Delete request
		 CloseableHttpResponse httpResponse = null;
		 httpResponse= httpClient.execute(httpPut);	//Delete execution
		 return httpResponse;
	 }

}
