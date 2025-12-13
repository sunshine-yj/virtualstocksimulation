//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 메시지 분리 클래스
package server;

import java.util.StringTokenizer;

public class ReceivedMSGTokenizer {
	StringTokenizer st;
	MSGTable mt = new MSGTable();
		
	public int detection(String _msg) {
		int result = -1;
		String tag;
		st = new StringTokenizer(_msg, "///");
		tag = st.nextToken();
		for(int i = 0; i<mt.numberOfMSG; i++) {
			if(tag.equals(mt.MSGtags[i])) {
				result = i;
				break;
			} else {
				
			}
		}
		return result;
	}

	// 메시지 추출 메소드
	// 아이디 관련 메소드
	// 아이디추출을 위한 메소드
	String findUID(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		return st.nextToken();  // 수신 메시지에서 사용자아이디 부분 추출 후 리턴
	}
	
	// 비밀번호추출을 위한 메소드
	String findPWD(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken(); // 수신 메시지에서 tag부분 추출
		st.nextToken(); // 수신 메시지에서 ID부분 추출
		return st.nextToken(); // 수신 메시지에서 PWD 추출
	}
	
	// 지갑을 위한 메소드
	public String findWallet(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken(); // 수신 메시지에서 tag부분 추출
		return st.nextToken(); // 수신 메시지에서 wallet 추출
	}
	
	
	// 주식 관련 메소드
	// 주식 검색 추출 메소드
	String findStock(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		return st.nextToken();  // 수신된 주식명
	}
	
	// 주식 즐겨찾기 추출 메소드
	String findfav(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 아이디부분 추출
		return st.nextToken();  // 수신된 주식명
	}
	
	// 주식 개수 추출 메소드
	int findStockCnt(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 아이디부분 추출
		st.nextToken();    // 수신된 주식명 추출
		return Integer.valueOf(st.nextToken());  // 수신된 주식 개수 추출
	}
	
	// 주식 개수 추출 메소드
	int findStockPrice(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 아이디부분 추출
		st.nextToken();    // 수신된 주식명 추출
		st.nextToken();    // 수신된 주식 개수 추출
		return Integer.valueOf(st.nextToken());  // 수신된 주식 가격
	}
	
	// 시뮬레이션
	// 주식 개수 추출 메소드
	int findYear(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 종목 추출
		return Integer.valueOf(st.nextToken());  // 수신된 년도
	}
	
	int findMonth(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 종목 추출
		st.nextToken();   // 수신된 년도
		return Integer.valueOf(st.nextToken());  // 수신된 월
	}
	
	
}
