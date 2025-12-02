package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

class ConnectedClient extends Thread{
	Socket socket;
	
	String uid = null;
	ArrayList<ConnectedClient> clients;
	ReceivedMSGTokenizer msgController = new ReceivedMSGTokenizer();
	MSGBuilder mBuilder = new MSGBuilder();
	
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
				this.setUID(msgController.findUID(msg));
				System.out.println(getUID() + " : " + msg);
				//ReceivedMSGTokenizer에서 클라이언트가 전송한 메시지 유형을 확인
				typeMSG = msgController.detection(msg);
				System.out.println(getUID() + " : " + typeMSG + "번 실행");
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
		
	}
}