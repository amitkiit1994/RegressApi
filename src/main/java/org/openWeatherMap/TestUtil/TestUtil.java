package org.openWeatherMap.TestUtil;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestUtil {

	
	public static String getValueByJPath(JSONObject responseJson, String jpath) {
		Object obj=responseJson;
		for(String s: jpath.split("/")) {
			if(!s.isEmpty()) {
				if(!(s.contains("[")||s.contains("]"))) {
					obj=((JSONObject) obj).get(s);
				}
				else if ((s.contains("[")||s.contains("]"))) {
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
					
				}
			}
		}
		return obj.toString();
	}
	
	public static String kelvinToDegree(String temp) {
		return String.valueOf(Math.round(Double.parseDouble(temp)-273.15));
	}
	
	public static String kelvinToFarheneit(String temp) {
		double result1=((Double.valueOf(temp)-273.15)*1.8)+32;
//		System.out.println("Farheneit: "+result1);
		long result=Math.round(result1);
		return String.valueOf(result);
	}
	public static boolean createFile(String filePath) {
		boolean fvar=false;
		try {
			File file=new File(filePath);
			fvar=file.createNewFile();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
		return fvar;
	}
}
