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
	
}
