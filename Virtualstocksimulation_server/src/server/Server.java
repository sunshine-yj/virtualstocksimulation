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
				server.ss = new ServerSocket(55555);
				System.out.println("Server> Server Socket is created....");
				while(true) {
					Socket socket = server.ss.accept();
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
