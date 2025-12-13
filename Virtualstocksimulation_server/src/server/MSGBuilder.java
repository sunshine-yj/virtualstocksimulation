//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 메시지 생성 클래스
package server;

import java.util.ArrayList;

import api.StockInformation;
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

	
	// 주식 검색결과 위한 정보 메시지
	String searchStockMSG(String itemName, int clPrice, Double fltRt, int vs, int trqu) {
		String madenMSG = null;
		if(itemName != null) {
			madenMSG =  "SEARCH" + "///" 
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
	
	// 보유주식 전달을 위한 정보 메시지
	String havStockMSG(int cnt) {
		String madenMSG = null;
		madenMSG =  "HAV" + "///" 
				+ cnt + "///" 
				+ "END";

		return madenMSG;
	}
	
	// 보유주식 전달을 위한 정보 메시지
	StringBuilder havStockListMSG(ArrayList<Stock> havStockList) {
		StringBuilder madenMSG = new StringBuilder();
		
		// 보유 주식 리스트 가져오기
		madenMSG.append("HAVLIST///");
		for(Stock s : havStockList) {
			madenMSG.append(s.getItemName()).append("///")
				.append(s.getHavCnt()).append("///")
				.append(s.getPrice()).append("///");
		}
		madenMSG.append("END");

		return madenMSG;
	}
	
	// 즐겨찾기 전달을 위한 정보 메시지
	StringBuilder favStockListMSG(ArrayList<Stock> favStockList) {
		StringBuilder madenMSG = new StringBuilder();
		
		// 즐겨찾기 리스트 가져오기
		madenMSG.append("FAVLIST///");
		for(Stock s : favStockList) {
			madenMSG.append(s.getItemName()).append("///");
		}
		madenMSG.append("END");

		return madenMSG;
	}
	
	// 주식 랭크 전달을 위한 메시지
	StringBuilder rankStockListMSG(ArrayList<StockInformation> list) {
		StringBuilder madenMSG = new StringBuilder();
		
		// 랭킹 주식 리스트 가져오기
		madenMSG.append("RANK///");
		for(StockInformation s : list) {
			madenMSG.append(s.getItemName()).append("///")
				.append(s.getClPrice()).append("///")
				.append(s.getFltRt()).append("///")
				.append(s.getTrqu()).append("///");
		}
		madenMSG.append("END");
		
		return madenMSG;
	}
	
	String simulMSG(int _oldPrice) {
		String madenMSG = null;

		madenMSG = "SIMULATION" + "///" 
				+ _oldPrice + "///" 
				+ "END";
		
		return madenMSG;
	}
}
