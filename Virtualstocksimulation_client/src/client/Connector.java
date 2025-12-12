package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Connector {
	Socket mySocket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream=null;
	MSGBuilder mBuilder = new MSGBuilder();
	ReceivedMSGTokenizer msgController = new ReceivedMSGTokenizer();
	ArrayList<Stock> havStockList = new ArrayList<>();
	
	public Connector() {
		try {
			mySocket = new Socket("localhost", 55555);
			System.out.println("CLIENT LOG> 서버로 연결되었습니다.");
			outStream = mySocket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = mySocket.getInputStream();
			dataInStream =  new DataInputStream(inStream);
			Thread.sleep(100);
			
//			while(true) {
//				
//			}
		
		}catch(Exception e) {
			
		}
		
	}
	// 로그인 메시지 전달 메소드
	public int sendLogin(String _uid, String pwd){
		boolean flag = false;
		String msg = null;
		int result = -1;
		try {
			dataOutStream.writeUTF(mBuilder.loginMSG(_uid, pwd));
			msg = dataInStream.readUTF();
		}catch(Exception e) {
			
		}
		
		result = msgController.findResult(msg);
		if(result != -1) {
			return 1;
		}else {
			return -1;
		}
	}
	
	// 회원가입 메시지 전달 메소드
	public void sendRegister(String _uid, String pwd){
		boolean flag = false;
		String msg = null;
		try {
			dataOutStream.writeUTF(mBuilder.registerMSG(_uid, pwd));
		}catch(Exception e) {
			
		}
	}
	// 검색 메시지 전달 메소드
	public String sendSearch(String _itemName) {
		String msg = null;
		try {
			dataOutStream.writeUTF(mBuilder.searchMSG(_itemName));
			msg = dataInStream.readUTF();
		}catch(Exception e) {
			
		}
		return msg;
	}
	// 즐겨찾기 전달 메소드
	public void sendFav(String _uid, String _itemName) {
		try {
			dataOutStream.writeUTF(mBuilder.favMSG(_uid, _itemName));
		}catch(Exception e) {
			
		}
	}
	
	// 보유금액 요청
	public int sendWalletView(String _uid) {
		String msg = null;
		int money = 0;
		try {
			dataOutStream.writeUTF(mBuilder.walletMSG(_uid));
			msg = dataInStream.readUTF();
			System.out.println(msg);
			
			money = msgController.findWallet(msg);
		}catch(Exception e) {
			
		}
		
		return money;
	}
	// 보유 주식 요청
	public String sendStock(String _uid, String _itemName) {
		String msg = null;

		try {
			dataOutStream.writeUTF(mBuilder.havStockMSG(_uid, _itemName));
			msg = dataInStream.readUTF();
		}catch(Exception e) {
			
		}
		
		return msg;
	}
	
	// 주식 구매 요청
	public void sendBuy(String _uid, String _itemName, int _itemCnt, int _price) {
		try {
			dataOutStream.writeUTF(mBuilder.buyMSG(_uid, _itemName, _itemCnt, _price));
		}catch(Exception e) {
			
		}
	}
	
	// 주식 구매 요청
	public void sendSell(String _uid, String _itemName, int _itemCnt, int _price) {
		String msg = null;
		int _money = -1;
		try {
			dataOutStream.writeUTF(mBuilder.sellMSG(_uid, _itemName, _itemCnt, _price));
		}catch(Exception e) {
			
		}
	}
	
	// 주식 리스트 요청
	public String sendHavList(String _uid) {
		String msg = null;
		
		try {
			dataOutStream.writeUTF(mBuilder.havListMSG(_uid));
			msg = dataInStream.readUTF();
			System.out.println(msg);
		}catch(Exception e) {
			
		}
		
		return msg;
	}
	
	public String sendFavList(String _uid) {
		String msg = null;
		
		try {
			dataOutStream.writeUTF(mBuilder.favListMSG(_uid));
			msg = dataInStream.readUTF();
			System.out.println(msg);
		}catch(Exception e) {
			
		}
		
		return msg;
	}
	
}
