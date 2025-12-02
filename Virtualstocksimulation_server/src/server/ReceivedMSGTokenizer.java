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
	
	String findUID(String _msg) {
		st = new StringTokenizer(_msg, "///");
		st.nextToken();   // 수신 메시지에서 tag부분 추출
		return st.nextToken();  // 수신 메시지에서 사용자아이디 부분 추출 후 리턴
	}
	
	
	
}
