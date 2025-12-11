package clientGUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import client.*;

public class LoginFrame extends JFrame {
	JPanel panel = new JPanel(new FlowLayout()); // 레이아웃 선언 
	JButton enter = new JButton("Login"); // Button enter 선언 
	JButton register = new JButton("Register"); // Button enter 선언
	JTextField typeId = new JTextField(); // id 받은곳  선언
	JPasswordField typePassword = new JPasswordField(); // password 받은곳 선언 받으면 ** < 처럼 나옴
	JLabel id = new JLabel("I   D"); // 라벨 type id
	JLabel password = new JLabel("Password"); // 라벨 type password
	static String user_id;
	Connector connector;
	Client mainOperator = null;
	
	// 로그인 창
	public LoginFrame(Client _o) {
		mainOperator = _o;
		connector = _o.connector;
		MyActionListener ml = new MyActionListener();
		setTitle("로그인");
		id.setPreferredSize(new Dimension(70, 30));
		typeId.setPreferredSize(new Dimension(300, 30));
		password.setPreferredSize(new Dimension(70, 30));
		typePassword.setPreferredSize(new Dimension(300, 30));
		enter.setPreferredSize(new Dimension(185, 30));
		register.setPreferredSize(new Dimension(185, 30));
		panel.add(id); //  ID 추가 
		panel.add(typeId); // 입력된 ID 추가 
		panel.add(password); // PASSWORD 추가 
		panel.add(typePassword); // 입력된 PASSWORD 추가 
		panel.add(enter);
		panel.add(register);
		setContentPane(panel);
		enter.addActionListener(ml); // Login 버튼에 이벤트 리스너 추가 
		register.addActionListener(ml); // Register 버튼에 이벤트 리스너 추가
		
		setResizable(false);
		setSize(400, 150);
		// 창을 닫으면 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//로그인화면을 중앙에 배치
		setLocationRelativeTo(null);

		setVisible(true);
	}
	
	class MyActionListener implements ActionListener  {
		//이벤트를 발생시킨 컴포넌트(소스)
		public void actionPerformed(ActionEvent e) {
			JButton b =  (JButton)e.getSource();
			if (b.getText().equals("Login")) {	// 로그인버튼을 누르면...
				//Password 컴포넌트에서 문자열 읽어오기 1
				String pwd = "";
				int result = -1;
				for(int i=0; i<typePassword.getPassword().length; i++) {
					pwd = pwd + typePassword.getPassword()[i];
				}
				System.out.println(typeId.getText()+ "//" + pwd);
				result = connector.sendLogin(typeId.getText(), pwd);
				if(result != -1 ) {
					user_id = typeId.getText();
					mainOperator.mf.updateWallet();
					mainOperator.mf.updateList();
					mainOperator.mf.setVisible(true);
					System.out.println("로그인 성공");
					dispose();
				}else {
					System.out.println("Log in Error~~~");
				}
								
				
			}else if (b.getText().equals("Register")) {
				mainOperator.rf.setVisible(true);
			}
		}
	}
	
	public String getUserId() {
		return user_id;
	}
	
	
}