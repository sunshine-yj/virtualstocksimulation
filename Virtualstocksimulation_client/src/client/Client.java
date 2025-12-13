//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 프로그램 실행 클래스
package client;

import clientGUI.*;

public class Client {
	
	public Connector connector;
	public LoginFrame lf;
	public MainFrame mf;
	public StockInfoFrame sf;
	public RegisterFrame rf;
	
	public static void main(String[] args) {
	
		Client client = new Client();
		client.connector = new Connector();
		client.mf = new MainFrame(client);
		
		client.rf = new RegisterFrame(client);
		
		client.lf = new LoginFrame(client);
		
	
	}

}
