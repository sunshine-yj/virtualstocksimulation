package db;

public class Stock {
	String itemName;
	int havCnt;
	int price;
	
	Stock(String itemName, int havCnt, int price) {
		this.itemName = itemName;
		this.havCnt = havCnt;
		this.price = price;
	}
	
	public String getItemName() {
		return itemName;
	}
	public int getHavCnt() {
		return havCnt;
	}
	public int getPrice() {
		return price;
	}
	
}
