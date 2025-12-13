package client;

public class Stock {
	String itemName;
	int havCnt;
	int price;
	int trqu;
	double FltRt;
	
	Stock(String itemName, int havCnt, int price) {
		this.itemName = itemName;
		this.havCnt = havCnt;
		this.price = price;
	}
	
	Stock(String itemName, int price, int trqu, double FltRt) {
		this.itemName = itemName;
		this.price = price;
		this.FltRt = FltRt;
		this.trqu = trqu;
	}
	
	Stock(String itemName) {
		this.itemName = itemName;
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
	public int getTrqu() {
		return trqu;
	}

	public double getFltRt() {
		return FltRt;
	}
	
}
