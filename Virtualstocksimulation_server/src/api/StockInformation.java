package api;

import java.util.*;
import java.util.ArrayList;

public class StockInformation {
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
	
	
}
