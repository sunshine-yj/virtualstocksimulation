//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 주식 정보 다이어로그 창
package clientGUI;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import client.*;

public class StockInfoFrame extends JFrame {
	
	// 주식 정보
	String itemName;
	int clPrice;
	Double fltRt;
	int vs;
	int trqu;
	
	// 내 주식 정보
	String hav_msg;
	int hav_cnt = 0;
	
	// 보유 금액
	int wallet = 0;
	CardLayout cardLayout = new CardLayout();
	JPanel cardPanel = new JPanel(cardLayout);
	// 주식 구매 판매 
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel favsimPanel = new JPanel(new FlowLayout());
	JPanel mainPanel = new JPanel(new FlowLayout());
	JPanel sellBuyPanel = new JPanel(new FlowLayout());
	
	// 주식 시뮬레이션
	JPanel panel2 = new JPanel(new BorderLayout());
	JPanel mainPanel2 = new JPanel(new BorderLayout());
	JPanel dateSearch = new JPanel(new FlowLayout());

	JComboBox<Integer> yearComboBox = new JComboBox<>(); // 년도
	JComboBox<Integer> monthComboBox = new JComboBox<>(); // 월

	
	Connector connector = new Connector();
	Client mainOperator = null;
	
	ReceivedMSGTokenizer rt = new ReceivedMSGTokenizer();
	
	public StockInfoFrame(Client _o, String _msg, int _money) {
		mainOperator = _o;
		this.wallet = _money;
		
		setTitle("주식 정보");
		setSize(400, 400);
		// 메인화면을 중앙으로 배치
		setLocationRelativeTo(null);
		MyActionListener ml = new MyActionListener();
		
		// 주식 정보 변환
		itemName = rt.finditemName(_msg);
		clPrice = rt.findclPrice(_msg);
		fltRt = rt.findfltRt(_msg);
		vs = rt.findvs(_msg);
		trqu = rt.findtrqu(_msg);
		
		// 주식 예상 금액 계산
		JButton simButton = new JButton("주식계산기");
		simButton.addActionListener(ml);
		simButton.setPreferredSize(new Dimension(185, 30));
		favsimPanel.add(simButton);
		
		
		// 즐겨찾기 버튼 배치
		JButton favButton = new JButton("즐겨찾기");
		favButton.addActionListener(ml);
		favButton.setPreferredSize(new Dimension(185, 30));
		favsimPanel.add(favButton);
		panel1.add(favsimPanel, BorderLayout.NORTH);
		
		
		// 주식 정보 출력
		JLabel labelitemName = new JLabel("주식명 : ");
		labelitemName.setPreferredSize(new Dimension(70, 30));
		JTextField TextitemName = new JTextField(itemName);
		TextitemName.setEditable(false); // 변경금지
		TextitemName.setPreferredSize(new Dimension(300, 30));
		
		JLabel labelclPrice = new JLabel("종 가 : ");
		labelclPrice.setPreferredSize(new Dimension(70, 30));
		JTextField TextclPrice = new JTextField(String.valueOf(clPrice));
		TextclPrice.setEditable(false);
		TextclPrice.setPreferredSize(new Dimension(300, 30));
		
		JLabel labelfltRt = new JLabel("상승률 : ");
		labelfltRt.setPreferredSize(new Dimension(70, 30));
		JTextField TextfltRt = new JTextField(String.valueOf(fltRt));
		TextfltRt.setEditable(false);
		TextfltRt.setPreferredSize(new Dimension(300, 30));
		
		JLabel labelvs = new JLabel("상승가 : ");
		labelvs.setPreferredSize(new Dimension(70, 30));
		JTextField Textvs = new JTextField(String.valueOf(vs));
		Textvs.setEditable(false);
		Textvs.setPreferredSize(new Dimension(300, 30));
		
		JLabel labeltrqu = new JLabel("거래량 : ");
		labeltrqu.setPreferredSize(new Dimension(70, 30));
		JTextField Texttrqu = new JTextField(String.valueOf(trqu));
		Texttrqu.setEditable(false);
		Texttrqu.setPreferredSize(new Dimension(300, 30));
		
		
		// 패널에 추가
		mainPanel.add(labelitemName);
		mainPanel.add(TextitemName);
		
		mainPanel.add(labelclPrice);
		mainPanel.add(TextclPrice);
		
		mainPanel.add(labelfltRt);
		mainPanel.add(TextfltRt);
		
		mainPanel.add(labelvs);
		mainPanel.add(Textvs);
		
		mainPanel.add(labeltrqu);
		mainPanel.add(Texttrqu);
		panel1.add(mainPanel, BorderLayout.CENTER);
		
		// 보유 주식 불러오기
		hav_msg = connector.sendStock(mainOperator.lf.getUserId(), itemName);
		hav_cnt = Integer.valueOf(rt.havStock(hav_msg));
		
		
		
		// 주식 구매 판매 버튼 배치
		JButton sell = new JButton("판 매");
		JButton buy = new JButton("구 매");
		sell.setPreferredSize(new Dimension(185, 30));
		sell.setForeground(Color.WHITE);
		sell.setBackground(Color.RED);
		sell.addActionListener(e -> {
			String cnt = JOptionPane.showInputDialog("판매 개수 입력:");
			int stockCnt = Integer.valueOf(cnt);
			int money = stockCnt(stockCnt, clPrice);
			// 구매 주식 정보 전달 위치
			if (hav_cnt >= stockCnt) {
				connector.sendSell(mainOperator.lf.getUserId(), itemName, stockCnt, clPrice);
				
				wallet = connector.sendWalletView(mainOperator.lf.getUserId());
				// 변경된 정보 최신화
				mainOperator.mf.updateWallet();
				mainOperator.mf.updateList();
				
				
				JOptionPane.showMessageDialog(this, "판매하였습니다.");
			} else {
				JOptionPane.showMessageDialog(this, "보유주식이 부족합니다.");
			}
			
		});
		
		buy.setPreferredSize(new Dimension(185, 30));
		buy.setForeground(Color.WHITE);
		buy.setBackground(Color.BLUE);
		buy.addActionListener(e -> {
			String cnt = JOptionPane.showInputDialog("구매 개수 입력:");
			int stockCnt = Integer.valueOf(cnt);
			int money = stockCnt(stockCnt, clPrice);
			if(wallet < money) {
				JOptionPane.showMessageDialog(this, "보유금액이 부족합니다.");
			} else if (wallet >= money) {
				// 구매 주식 정보 전달 위치
				
				connector.sendBuy(mainOperator.lf.getUserId(), itemName, stockCnt, clPrice);
				
				wallet = connector.sendWalletView(mainOperator.lf.getUserId());
				// 변경된 정보 최신화
				mainOperator.mf.updateWallet();
				mainOperator.mf.updateList();

				JOptionPane.showMessageDialog(this, "구매하였습니다.");
			} else {
				System.out.println("주식 구매 버튼 오류");
			}
		});
		
		sellBuyPanel.add(sell);
		sellBuyPanel.add(buy);
		panel1.add(sellBuyPanel, BorderLayout.SOUTH);
		
		
		// 주식 시뮬레이션
		
		// 뒤로 가기 버튼
		JButton back = new JButton("뒤로가기");
		back.setPreferredSize(new Dimension(185, 30));
		back.addActionListener(e -> {
			cardLayout.show(cardPanel, "stockinfo");
		});
		
		// 오늘 일자
		int today = LocalDate.now().getYear();
		// 년도 콤보박스
		for(int year = 2021; year <= today; year++) {
			yearComboBox.addItem(year);
		}
		// 월 콤보 박스
		for(int month = 1; month <= 12; month++) {
			monthComboBox.addItem(month);
		}
		yearComboBox.setPreferredSize(new Dimension(185, 30));
		monthComboBox.setPreferredSize(new Dimension(185, 30));
		JLabel oldstockCntLabel = new JLabel("주식개수 : ");
		oldstockCntLabel.setPreferredSize(new Dimension(70, 30));
		JTextField stockCnt = new JTextField();
		stockCnt.setPreferredSize(new Dimension(300, 30));
		
		
		JLabel stockCntLabel = new JLabel("현재 총 가격 : ");
		stockCntLabel.setPreferredSize(new Dimension(70, 30));
		JTextField stockSumnow = new JTextField();
		stockSumnow.setPreferredSize(new Dimension(300, 30));
		stockSumnow.setEditable(false);


		// 출력 버튼
		JButton simulationButton = new JButton("출력");
		simulationButton.addActionListener(e -> {
			
		});

		
		// 시뮬레이션 배치
		mainPanel2.add(back, BorderLayout.NORTH);
		dateSearch.add(yearComboBox);
		dateSearch.add(monthComboBox);
		dateSearch.add(oldstockCntLabel);
		dateSearch.add(stockCnt);
		dateSearch.add(stockCntLabel);
		dateSearch.add(stockSumnow);

		mainPanel2.add(dateSearch, BorderLayout.CENTER);
		panel2.add(mainPanel2);
		
		
		cardPanel.add(panel1, "stockinfo");
		cardPanel.add(panel2, "stockcalc");

		setContentPane(cardPanel);
		setVisible(true);
	}
	
	// 주식 계산
	public int stockCnt(int _cnt, int _money) {
		int num = 0;
		num = _cnt * _money;
		return num;
	}
	
	class MyActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			if(button.getText().equals("즐겨찾기")) {
				String getItemName = itemName;
				String user_id = mainOperator.lf.getUserId();
				connector.sendFav(user_id, getItemName);
				mainOperator.mf.updatefavList();
			}
			else if (button.getText().equals("주식계산기")) {
				cardLayout.show(cardPanel, "stockcalc");
			}
		}
	}
	
}
