package client;

import clientGUI.*;

public class Client {
	public Connector connector;
	public LoginFrame lf;
	public MainFrame mf;
	public StockInfoFrame sf;
	public RegisterFrame rf;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		client.connector = new Connector();
		client.lf = new LoginFrame(client);
		client.rf = new RegisterFrame(client);
		client.mf = new MainFrame(client);
		
	}

}
