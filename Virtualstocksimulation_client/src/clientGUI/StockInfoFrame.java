package clientGUI;

import java.awt.*;
import java.awt.event.*;
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
	
	JPanel panel = new JPanel(new BorderLayout());
	JPanel favPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JPanel mainPanel = new JPanel(new FlowLayout());
	JPanel sellBuyPanel = new JPanel(new FlowLayout());
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
//		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); 보류
		MyActionListener ml = new MyActionListener();
		
		// 주식 정보 변환
		itemName = rt.finditemName(_msg);
		clPrice = rt.findclPrice(_msg);
		fltRt = rt.findfltRt(_msg);
		vs = rt.findvs(_msg);
		trqu = rt.findtrqu(_msg);
		
		// 즐겨찾기 버튼 배치
		JButton favButton = new JButton("즐겨찾기");
		favButton.addActionListener(ml);
		favButton.setPreferredSize(new Dimension(185, 30));
		favPanel.add(favButton);
		panel.add(favPanel, BorderLayout.NORTH);
		
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
		panel.add(mainPanel, BorderLayout.CENTER);
		
		
		
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
//				wallet += money;
			// 구매 주식 정보 전달 위치
			if (hav_cnt >= stockCnt) {
				connector.sendSell(mainOperator.lf.getUserId(), itemName, stockCnt, clPrice);
				
				wallet = connector.sendWalletView(mainOperator.lf.getUserId());
				mainOperator.mf.updateWallet();
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
//				wallet -= money;
				// 구매 주식 정보 전달 위치
				
				connector.sendBuy(mainOperator.lf.getUserId(), itemName, stockCnt, clPrice);
				
				wallet = connector.sendWalletView(mainOperator.lf.getUserId());
				mainOperator.mf.updateWallet();
				JOptionPane.showMessageDialog(this, "구매하였습니다.");
			} else {
				System.out.println("주식 구매 버튼 오류");
			}
		});
		
		sellBuyPanel.add(sell);
		sellBuyPanel.add(buy);
		panel.add(sellBuyPanel, BorderLayout.SOUTH);
		
		setContentPane(panel);
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
			}
		}
	}
	
}
