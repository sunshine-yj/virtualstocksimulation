//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 회원가입을 위한 UI
package clientGUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import client.*;

public class RegisterFrame extends JFrame {
	JPanel panel = new JPanel(new FlowLayout()); // 레이아웃 선언 
	JButton register = new JButton("확인"); // Button enter 선언 
	JButton cancel = new JButton("취소"); // Button enter 선언
	JTextField typeId = new JTextField(); // id 받은곳  선언
	JTextField typePassword = new JTextField(); // password 받은곳 선언 받으면 ** < 처럼 나옴
	JLabel id = new JLabel("아 이 디"); // 라벨 type id
	JLabel password = new JLabel("비밀번호"); // 라벨 type password
	static String user_id;
	Connector connector;
	Client mainOperator = null;
	
	// 로그인 창
	public RegisterFrame(Client _o) {
		mainOperator = _o;
		connector = _o.connector;
		MyActionListener ml = new MyActionListener();
		setTitle("회원가입");
		id.setPreferredSize(new Dimension(70, 30));
		typeId.setPreferredSize(new Dimension(300, 30));
		password.setPreferredSize(new Dimension(70, 30));
		typePassword.setPreferredSize(new Dimension(300, 30));
		register.setPreferredSize(new Dimension(185, 30));
		cancel.setPreferredSize(new Dimension(185, 30));
		panel.add(id); //  ID 추가 
		panel.add(typeId); // 입력된 ID 추가 
		panel.add(password); // PASSWORD 추가 
		panel.add(typePassword); // 입력된 PASSWORD 추가 
		panel.add(register);
		panel.add(cancel);
		setContentPane(panel);
		register.addActionListener(ml); // Login 버튼에 이벤트 리스너 추가 
		cancel.addActionListener(ml); // Cancel 버튼에 이벤트 리스너 추가
		
		
		setResizable(false);
		setSize(400, 150);
		//로그인화면을 중앙에 배치
		setLocationRelativeTo(null);

		setVisible(false);
	}
	
	class MyActionListener implements ActionListener  {
		//이벤트를 발생시킨 컴포넌트(소스)
		public void actionPerformed(ActionEvent e) {
			JButton b =  (JButton)e.getSource();
			if (b.getText().equals("확인")) {
				connector.sendRegister(typeId.getText(), typePassword.getText());
				dispose();
			}else if (b.getText().equals("취소")) {
				typeId.setText("");
				typePassword.setText("");
			}
		}
	}
	
	public String getUserId() {
		return user_id;
	}
	
	
}