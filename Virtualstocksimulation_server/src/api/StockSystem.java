//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : API를 불러오는 클래스
package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StockSystem {
	
	private static final String SERVICE_KEY = "VBW8uhXgyKAauuCP87mKsvrC9zB38NHbAsM5asJVN9tBFuT9PeFfa%2BFYHpM3z69wprsEQnJsU5kdEzD%2BBwezaA%3D%3D";
	private static final String API_URL = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
	
	
	// 주식 검색을 위한 메소드
	public StockInformation searchIteam(String itemName) {
		LocalDate date = LocalDate.now().minusDays(3);
		//https://breakcoding.tistory.com/120
		//주소에서 참고하여 사용하였습니다.
		DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyyMMdd");
		String today = date.format(dateTime);
		try {
			return resultStock(today, itemName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private StockInformation resultStock(String _date, String _itemName) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder(API_URL);
		urlBuilder.append("?serviceKey=").append(SERVICE_KEY);
		urlBuilder.append("&pageNo=1"); // 페이지 쪽수
		urlBuilder.append("&numOfRows=1");
		urlBuilder.append("&resultType=json");
		urlBuilder.append("&basDt=").append(_date);
		urlBuilder.append("&itmsNm=").append((URLEncoder.encode(_itemName, "UTF-8")));
		
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
            
            String itemNm = obj.get("itmsNm").getAsString();// 주식명
            int clPrice = obj.get("clpr").getAsInt();// 종가
            Double fltRt = obj.get("fltRt").getAsDouble();// 등락률
            int vs = obj.get("vs").getAsInt(); // 등락
            int trqu = obj.get("trqu").getAsInt(); // 거래량
        
            return new StockInformation(itemNm, clPrice, fltRt, vs, trqu);
        }
		
		return null;
	}
}
