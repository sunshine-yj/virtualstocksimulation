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
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StockSystem {
	
	private static final String SERVICE_KEY = "VBW8uhXgyKAauuCP87mKsvrC9zB38NHbAsM5asJVN9tBFuT9PeFfa%2BFYHpM3z69wprsEQnJsU5kdEzD%2BBwezaA%3D%3D";
	private static final String API_URL = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
	
	
	// 주식 검색을 위한 메소드
	public StockInformation searchIteam(String itemName) {
		StockInformation item;
		LocalDate date = LocalDate.now().minusDays(1);
		//https://breakcoding.tistory.com/120
		//주소에서 참고하여 사용하였습니다.
		DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyyMMdd");

		for(int i = 30; i > 0; i--) {
			String today = date.format(dateTime);
			try {
				item = resultStock(today, itemName);
				if(item != null) {
					return item;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			date = date.minusDays(1);

		}
		return null;
	}
	
	// 주식 시뮬레이션을 위한 메소드
	public StockInformation simulIteam(String itemName, int _year, int _month) {
		StockInformation item;
		LocalDate day = LocalDate.of(_year, _month, 02);
		System.out.println(day);
		//https://breakcoding.tistory.com/120
		//주소에서 참고하여 사용하였습니다.
		DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		for(int i = 30; i > 0; i--) {
			String today = day.format(dateTime);

			try {
				item = resultStock(today, itemName);
				if(item != null) {
					return item;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			day = day.minusDays(1);
		}
		
		return null;
	}
	
	
	// 주식 랭크을 위한 메소드
	public ArrayList<StockInformation> rankIteam() {
		ArrayList<StockInformation> list = new ArrayList<>();
		ArrayList<StockInformation> newlist = new ArrayList<>();
		
		LocalDate date = LocalDate.now().minusDays(4);
		DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyyMMdd");
		String today = date.format(dateTime);
		
		try {
			// https://hianna.tistory.com/569를 참고하였습니다.
			list = resultStock(today);
			Collections.sort(list, Collections.reverseOrder());
			
			for(int i = 0; i < list.size(); i++) {
				newlist.add(list.get(i));
			}
			
			
			return newlist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	// 주식 차트 정보를 위한 메소드
	public ArrayList<Integer> chartIteam(String _itemName) {
		ArrayList<Integer> list = new ArrayList<>();

		LocalDate date = LocalDate.now().minusDays(1);
		DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		for(int i = 0; i < 12; i++) {
			
			LocalDate months = LocalDate.now().minusMonths(i);
			
			for(int j = 30; j > 0; j--) {
				String today = months.format(dateTime);

				try {
					int price = chartStock(today, _itemName);
					if (price > 0) {
			            list.add(price);
			            break;
			        }
				} catch (Exception e) {
					e.printStackTrace();
				}
				months = months.minusDays(1);
			}
		}
		return list;
	}
	
	
	private StockInformation resultStock(String _date, String _itemName) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder(API_URL);
		urlBuilder.append("?serviceKey=").append(SERVICE_KEY);
		urlBuilder.append("&pageNo=1"); // 페이지 쪽수
		urlBuilder.append("&numOfRows=1");
		urlBuilder.append("&resultType=json");
		urlBuilder.append("&basDt=").append(_date);
		urlBuilder.append("&itmsNm=").append((URLEncoder.encode(_itemName, "UTF-8"))); // AI를 통하여 종목명 입력값의 문제를 해결
		
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
	
	
	private int chartStock(String _date, String _itemName) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder(API_URL);
		urlBuilder.append("?serviceKey=").append(SERVICE_KEY);
		urlBuilder.append("&pageNo=1"); // 페이지 쪽수
		urlBuilder.append("&numOfRows=1");
		urlBuilder.append("&resultType=json");
		urlBuilder.append("&basDt=").append(_date);
		urlBuilder.append("&itmsNm=").append((URLEncoder.encode(_itemName, "UTF-8"))); // AI를 통하여 종목명 입력값의 문제를 해결
		
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
            
            int clPrice = obj.get("clpr").getAsInt();// 종가
            
            return clPrice;
        }
		
		return 0;
	}
	
	
	// 주식 거래량 순위
	private ArrayList<StockInformation> resultStock(String _date) throws Exception {
		ArrayList<StockInformation> list = new ArrayList<>();
		
		for(int j = 0; j < 4; j++) {
			StringBuilder urlBuilder = new StringBuilder(API_URL);
			urlBuilder.append("?serviceKey=").append(SERVICE_KEY);
			urlBuilder.append("&pageNo=").append(j); // 페이지 쪽수
			urlBuilder.append("&numOfRows=4");
			urlBuilder.append("&resultType=json");
			urlBuilder.append("&basDt=").append(_date);
			urlBuilder.append("&beginTrqu=").append(5000000);
			
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
	            
	            list.add(new StockInformation(itemNm, clPrice, fltRt, vs, trqu));
	        
	        }
			
		}
		
		return list;
	}
	
}
