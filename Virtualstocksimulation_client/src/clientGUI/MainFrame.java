package clientGUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import client.*;

public class MainFrame extends JFrame {
	StockInfoFrame sif;
	int _money = 0;
	// 배치할 패널
	JPanel mainPanel = new JPanel(new GridLayout(2,2));// 메인
	// 검색 패널
	JPanel searchPanel = new JPanel(new FlowLayout());// 검색기능
	JLabel searchLabel = new JLabel("주식명 : ");
	JTextField typeItemNm = new JTextField(); // 검색 창
	JButton search = new JButton("검 색");

	
	JPanel walletPanel = new JPanel();// 보유금액 송출
	JLabel walletLabel = new JLabel("보유금액 : ");
	JTextField typeWallet = new JTextField(); // 출력 창
	
	JPanel panel2 = new JPanel();// 인기 주식 송출
	JPanel panel3 = new JPanel();// 즐겨찾기 모델
	
	DefaultTableModel tableModel;
//	String[] header = {"종목명", "현재가", "상승가", "상승률", "거래량"};
//	
////	DefaultTableModel model = new DefaultTableModel(header, body);
//	JTable table = new JTable(body, header);
	
	Connector connector;
	Client mainOperator = null;
	
	public MainFrame(Client _o) {
		mainOperator = _o;
		connector = _o.connector;
		// mainPanel 구성
		setTitle("가상 주식 어플리케이션");
		setSize(900, 500);
		// 창을 닫으면 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 메인화면을 중앙으로 배치
		setLocationRelativeTo(null);
//		MyActionListener ml = new MyActionListener();
		
		// 검색 창
		typeItemNm.setPreferredSize(new Dimension(300, 30));
		searchPanel.add(searchLabel);
		searchPanel.add(typeItemNm);
		searchPanel.add(search);
		search.addActionListener(e -> {
			String msg = connector.sendSearch(typeItemNm.getText());
			System.out.println("검색 버튼 : " + typeItemNm.getText());
			if(msg != null) {
				sif = new StockInfoFrame(mainOperator, msg, _money);
			} else {
				System.out.println("msg값이 없음");
			}
		});
		mainPanel.add(searchPanel);
		
		typeWallet.setPreferredSize(new Dimension(100, 30));
		typeWallet.setEditable(false);
		
		
		walletPanel.add(walletLabel);
		walletPanel.add(typeWallet);
		mainPanel.add(walletPanel);
		
//		tableModel = new DefaultTableModel(body, header){ // Java 프로젝트 수업에 사용한 코드 응용
//			// 셀 편집이 불가능하게 설정 
//			public boolean isCellEditable(int body, int header) {
//				return false;
//			}
//		};
//		table.setModel(tableModel);
//		panel2.add(new JScrollPane(table));
		mainPanel.add(panel2);
		
		
		
		
		
		mainPanel.add(panel3);
		
		setContentPane(mainPanel);
		setVisible(false); // 임시 실행시 true
	}
	
	public void updateWallet() {
		_money = connector.sendWalletView(mainOperator.lf.getUserId());
		typeWallet.setText(String.valueOf(_money));
	}
	/*
	class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			if(button.getText().equals("검 색")) {
				String msg = connector.sendSearch(typeItemNm.getText());
				System.out.println("검색 버튼 : " + typeItemNm.getText());
				if(msg != null) {
					new StockInfoFrame(mainOperator, msg);
				} else {
					System.out.println("msg값이 없음");
				}
			}
		}
		
	}
	*/
}
