package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import db.*;
import api.*;

class ConnectedClient extends Thread{
	Socket socket;
	
	String uid = null;
	ArrayList<ConnectedClient> clients;
	ReceivedMSGTokenizer msgController = new ReceivedMSGTokenizer();
	MSGBuilder mBuilder = new MSGBuilder();
	StockInformation sf;
	StockSystem ss = new StockSystem();
	UserDAO ud = new UserDAO();
	StockDAO sd = new StockDAO();
	
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream inStream;
	DataInputStream dataInStream;
	
	ConnectedClient(Socket _s, ArrayList<ConnectedClient> _cl){
		this.socket = _s; 
		this.clients = _cl;
	}
	
	public void run() {
		int typeMSG;
		try {
			System.out.println("Server> "+ this.socket.toString()+"에서의 접속이 연결되었습니다.");
			
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			// 메시지 수신 및 전송
			while(true) {
				// 클라이언트가 전송한 메시지 패킷을 받는다
				String msg = dataInStream.readUTF();
				System.out.println(msg);
				this.setUID(msgController.findUID(msg));
				System.out.println(getUID() + " : " + msg);
				//ReceivedMSGTokenizer에서 클라이언트가 전송한 메시지 유형을 확인
				typeMSG = msgController.detection(msg);
				System.out.println(getUID() + " : " + msg + "," + typeMSG + "번 실행");
				//확인된 메시지 유형에 따라 메시지를 정형화하여 클라이언트에 전송
				msgBasedService(typeMSG, msg);

			}			
		}
		catch(IOException e) {
			System.out.println("Server> "+ this.socket.toString()+": 연결해제됨");
		}
	}
	
	void setUID(String _uid) {
		this.uid = _uid;
	}
	
	String getUID() {
		if(this.uid != null) {
			return this.uid;
		}
		else return "No_ID";
	}
	
	
	// 서버 메시지 규격 전송	
	void msgBasedService(int _type, String _msg) {
		if(_type == 0) {
			String _smsg = null;
			this.setUID(msgController.findUID(_msg));
			_smsg = mBuilder.loginMSG(loginCheak(_msg), getUID());
			try {
				dataOutStream.writeUTF(_smsg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(_type == 1) {
			String _smsg = null;
			String StockName = msgController.findStock(_msg);
			sf = ss.searchIteam(StockName);
			_smsg = mBuilder.searchStockMSG(sf.getItemName(), sf.getClPrice(), sf.getFltRt(), sf.getVs(), sf.getTrqu());
			System.out.println("SEARCH: " + _smsg);
			try {
				dataOutStream.writeUTF(_smsg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(_type == 2) {
			favPlus(_msg);
		}
		if(_type == 3) {
			RegisterUser(_msg);
		}
		if(_type == 4) {
			String _smsg = null;
			int money = walletUser(_msg);
			_smsg = mBuilder.moneyMSG(money);
			System.out.println("WALLET: " + _smsg);
			try {
				dataOutStream.writeUTF(_smsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(_type == 5) {
			buyStock(_msg);
		}
		if(_type == 6) {
			sellStock(_msg);
		}
		if(_type == 7) {
			String _smsg = null;
			int cnt = sd.havStock(msgController.findUID(_msg), msgController.findfav(_msg));
			_smsg = mBuilder.havStockMSG(cnt);
			try {
				dataOutStream.writeUTF(_smsg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(_type == 8) {
			ArrayList<Stock> havStockList = new ArrayList<>();
			String _smsg = null;
			
			havStockList = sd.havStockList(msgController.findUID(_msg));
			_smsg = String.valueOf(mBuilder.havStockListMSG(havStockList));
			System.out.println(_smsg); // 확인
			try {
				dataOutStream.writeUTF(_smsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// 기능 실행 메소드
	// 로그인 확인 메소드
	int loginCheak(String _msg) {
		int result = -1;
		String _uid = msgController.findUID(_msg);
		String _pwd = msgController.findPWD(_msg);
		
		result = ud.loginUser(_uid, _pwd);
		
		return result;// 로그인 성공시 1반환
	}
	
	// 즐겨찾기 추가 메소드
	void favPlus(String _msg) {
		String _uid = msgController.findUID(_msg);
		String sName = msgController.findfav(_msg);
		
		sd.insertFav(_uid, sName);
	}
	// 회원가입 메소드
	void RegisterUser(String _msg) {
		String _uid = msgController.findUID(_msg);
		String _pwd = msgController.findPWD(_msg);
		
		ud.insertUser(_uid, _pwd);
	}
	// 지갑 호출
	int walletUser(String _msg) {
		String _uid = msgController.findWallet(_msg);
		
		return ud.walletUser(_uid);
	}
	// 주식 구매
	void buyStock(String _msg) {
		String _uid = msgController.findUID(_msg);
		String _itemName = msgController.findfav(_msg);
		int _itemCnt = msgController.findStockCnt(_msg);
		int _price = msgController.findStockPrice(_msg);
		
		sd.insertStock(_uid, _itemName, _itemCnt, _price);
		int minus = _itemCnt * _price;
		ud.walletMinus(_uid, minus);
	}
	
	// 주식 판매
	void sellStock(String _msg) {
		String _uid = msgController.findUID(_msg);
		String _itemName = msgController.findfav(_msg);
		int _itemCnt = msgController.findStockCnt(_msg);
		int _price = msgController.findStockPrice(_msg);
		
		sd.sellStock(_uid, _itemName, _itemCnt, _price);
		int plus = _itemCnt * _price;
		ud.walletPlus(_uid, plus);
	}
	
}