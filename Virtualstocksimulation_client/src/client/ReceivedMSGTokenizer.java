package client;

import java.util.*;
import client.*;

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
	
	// 유저 관련 메소드
	// 아이디추출을 위한 메소드
	public String findUID(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		return st.nextToken();  // 수신 메시지에서 사용자아이디 부분 추출 후 리턴
	}
	
	// 아이디추출을 위한 메소드
	public int findResult(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 아이디부분 추출
		return Integer.valueOf(st.nextToken());  // 수신 메시지에서 결과 부분 추출 후 리턴
	}
		
	
	// 지갑을 위한 메소드
	public int findWallet(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken(); // 수신 메시지에서 tag부분 추출
		return Integer.valueOf(st.nextToken()); // 수신 메시지에서 wallet 추출
	}
	
	// 주식 분리
	public String finditemName(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		return st.nextToken();  // 수신 메시지에서 주식명 추출
	}
	// 주식 종가
	public int findclPrice(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 주식명 추출
		return Integer.valueOf(st.nextToken());  // 수신 메시지에서 종가 추출
	}
	// 주식 등락률
	public Double findfltRt(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 주식명 추출
		st.nextToken();   // 수신 메시지에서 종가 추출
		return Double.valueOf(st.nextToken());  // 수신 메시지에서 상승률 추출
	}
	
	// 주식 등락률
	public Integer findvs(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 주식명 추출
		st.nextToken();   // 수신 메시지에서 종가 추출
		st.nextToken();   // 수신 메시지에서 상승률 추출
		return Integer.valueOf(st.nextToken());  // 수신 메시지에서 상승가 추출
	}
	
	// 주식 등락률
	public Integer findtrqu(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		st.nextToken();   // 수신 메시지에서 주식명 추출
		st.nextToken();   // 수신 메시지에서 종가 추출
		st.nextToken();   // 수신 메시지에서 상승률 추출
		st.nextToken();   // 수신 메시지에서 상승가 추출
		return Integer.valueOf(st.nextToken());  // 수신 메시지에서 거래량 추출
	}
}
