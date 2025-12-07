package server;

import db.*;

public class MSGBuilder {
	UserDAO ud = new UserDAO();
	
	// 로그인 확인 전달 메시지
	String loginMSG(int result, String _uid) {
		String madenMSG = null;
		int money = -1;
		money = ud.walletUser(_uid);
		madenMSG =  "LOGIN" + "///" 
				+ _uid + "///"
				+ result + "///"
				+ "END";

		return madenMSG;
	}
	
	// 인기 차트 정보 전달 메시지
	String topStockMSG(String stockName, int stockMoney, int updownMoney, int sellcnt) {
		String madenMSG = null;
		madenMSG =  "TOPSTOCK" + "///" 
				+ stockName + "///"
				+ stockMoney + "///"
				+ updownMoney + "///"
				+ sellcnt + "///"
				+ "END";

		return madenMSG;
	}
	
	// 주식 검색결과 위한 정보 메시지
	String searchStockMSG(String itemName, int clPrice, Double fltRt, int vs, int trqu) {
		String madenMSG = null;
		if(itemName != null) {
			madenMSG =  "TOPSTOCK" + "///" 
					+ itemName + "///"
					+ clPrice + "///"
					+ fltRt + "///"
					+ vs + "///"
					+ trqu + "///"
					+ "END";
		} else {
		
		}

		return madenMSG;
	}
	
	// 보유금액 전달을 위한 정보 메시지
	String moneyMSG(int _money) {
		String madenMSG = null;
		madenMSG =  "WALLET" + "///" 
				+ _money + "///" 
				+ "END";

		return madenMSG;
	}
}
