package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

	ServerSocket ss = null;
	ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();
	public static void main(String[] args) {

			Server server = new Server();
			try {
				server.ss = new ServerSocket(55555);                    //서버 소켓 생성
				System.out.println("Server> Server Socket is created....");
				while(true) {                                                         //서버 메인쓰레드 소켓생성 무한루프
					Socket socket = server.ss.accept();	                          //소켓생성
					ConnectedClient c = new ConnectedClient(socket, server.clients);
					server.clients.add(c);
					c.start();
				}
			}catch(SocketException e){
				System.out.println("Server> 서버 종료");
			}catch(Exception e) {
				e.printStackTrace();
			}
	}

}
