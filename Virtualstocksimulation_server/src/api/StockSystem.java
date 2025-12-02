package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StockSystem {
	private static final String SERVICE_KEY = "VBW8uhXgyKAauuCP87mKsvrC9zB38NHbAsM5asJVN9tBFuT9PeFfa%2BFYHpM3z69wprsEQnJsU5kdEzD%2BBwezaA%3D%3D";
	private static final String API_URL = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
	
	
	private String fetchCurrentStock() throws Exception {
		String baseDate = null;
		String itmsNm = null;
		
		StringBuilder urlBuilder = new StringBuilder(API_URL);
		urlBuilder.append("?serviceKey=").append(URLEncoder.encode(SERVICE_KEY, "UTF-8"));
		urlBuilder.append("&pageNo=1");
		urlBuilder.append("&numOfRows=1");
		urlBuilder.append("&resultType=JSON");
		urlBuilder.append("&basDt=").append(baseDate);
		urlBuilder.append("&itmsNm=").append((URLEncoder.encode(itmsNm, "UTF-8")));
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );
		
        StringBuilder sb = new StringBuilder();
        String line;
        
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        
        br.close();
        conn.disconnect();
		
		
		// JSON 파싱
		JsonObject root = JsonParser.parseString(sb.toString()).getAsJsonObject();
        JsonObject response = root.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonObject items = body.getAsJsonObject("items");
        JsonArray itemArray = items.getAsJsonArray("item");
        
        for (int i = 0; i < itemArray.size(); i++) {
            JsonObject obj = itemArray.get(i).getAsJsonObject();
            
            String itemNm = obj.get(itmsNm).getAsString();
            String clPrice = obj.get("clpr").getAsString();
            String opPrice = obj.get("mkp").getAsString();
            String hiPrice = obj.get("hipr").getAsString();
            String loPrice = obj.get("lopr").getAsString();
            String result = itemNm + "///" + 
            		clPrice + "///" + 
            		opPrice + "///" + 
            		hiPrice + "///" + 
            		loPrice + "///" + 
            		"END";
            return result;
        }
		
		return null;
	}
}
