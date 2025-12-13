//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 주식 관리 및 비교 클래스
package api;

import java.util.*;
import java.util.ArrayList;

public class StockInformation implements Comparable<StockInformation>{
	String itemName; // 종목명
	int clPrice; // 종가
	Double fltRt; // 등락률
	int vs; // 등락
	int trqu; // 거래량
	
	StockInformation(String itemName, int clPrice, Double fltRt, int vs, int trqu) {
		this.itemName = itemName;
		this.clPrice = clPrice;
		this.fltRt = fltRt;
		this.vs = vs;
		this.trqu = trqu;
	}

	public String getItemName() {
		return itemName;
	}

	public int getClPrice() {
		return clPrice;
	}

	public Double getFltRt() {
		return fltRt;
	}

	public int getVs() {
		return vs;
	}

	public int getTrqu() {
		return trqu;
	}
	
	// https://hianna.tistory.com/569를 참고하여 정렬하였습니다.
	@Override
	public int compareTo(StockInformation o) {
		if(o.trqu < trqu) {
			return 1;
		} else if (o.trqu > trqu) {
			return -1;
		}
		
		return 0;
	}
	
	
}
