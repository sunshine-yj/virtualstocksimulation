package client;

public class MSGBuilder {
	// 로그인 확인 전달 메시지
	String loginMSG(String _uid, String _pwd) {
		String madenMSG = null;
		madenMSG =  "LOGIN" + "///" 
				+ _uid + "///"
				+ _pwd + "///"
				+ "END";

		return madenMSG;
	}
	// 회원가입 정보 전달 메시지
	String registerMSG(String _uid, String _pwd) {
		String madenMSG = null;
		madenMSG =  "REGISTER" + "///" 
				+ _uid + "///"
				+ _pwd + "///"
				+ "END";

		return madenMSG;
	}
	// 검색 메시지
	String searchMSG(String itemName) {
		String madenMSG = null;
		madenMSG =  "SEARCH" + "///" 
				+ itemName + "///"
				+ "END";

		return madenMSG;
	}
	// 즐겨찾기 메시지
	String favMSG(String _uid, String itemName) {
		String madenMSG = null;
		madenMSG =  "FAV" + "///" 
				+ _uid + "///"
				+ itemName + "///"
				+ "END";

		return madenMSG;
	}
	// 보유금액 요청
	String walletMSG(String _uid) {
		String madenMSG = null;
		madenMSG =  "WALLET" + "///" 
				+ _uid + "///"
				+ "END";

		return madenMSG;
	}
	// 구매 정보 전달
	String buyMSG(String _uid, String _itemName, int _itemCnt, int _money) {
		String madenMSG = null;
		madenMSG =  "BUY" + "///" 
				+ _uid + "///"
				+ _itemName + "///"
				+ _itemCnt + "///"
				+ _money + "///"
				+ "END";
		
		return madenMSG;
	}
	
	// 판매 정보 전달
	String sellMSG(String _uid, String _itemName, int _itemCnt, int _money) {
		String madenMSG = null;
		madenMSG =  "SELL" + "///" 
				+ _uid + "///"
				+ _itemName + "///"
				+ _itemCnt + "///"
				+ _money + "///"
				+ "END";
		
		return madenMSG;
	}
	
	// 보유 주식 요청
	String havStockMSG(String _uid, String _itemName) {
		String madenMSG = null;
		madenMSG =  "HAV" + "///" 
				+ _uid + "///"
				+ _itemName + "///"
				+ "END";
		
		return madenMSG;
	}
	
	
	
	// 주식 리스트 요청
	String havListMSG(String _uid) {
		String madenMSG = null;
		madenMSG =  "SELL" + "///" 
				+ _uid + "///"
				+ "END";
		
		return madenMSG;
	}
	
	
}
