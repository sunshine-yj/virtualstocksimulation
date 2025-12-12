package clientGUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import client.*;

public class MainFrame extends JFrame {
	
	StockInfoFrame sif;
	int _money = 0;
	// 배치할 패널
	JPanel mainPanel = new JPanel(new BorderLayout());// 메인
	JPanel topPanel = new JPanel(new FlowLayout());
	JPanel centerPanel = new JPanel(new FlowLayout());
	JPanel bottomPanel = new JPanel(new FlowLayout());
	// 검색 패널
	JPanel searchPanel = new JPanel(new FlowLayout());// 검색기능
	JLabel searchLabel = new JLabel("주식명 : ");
	JTextField typeItemNm = new JTextField(); // 검색 창
	JButton search = new JButton("검 색");

	// 사이 공간 패널
	JPanel non = new JPanel();
	JPanel non1 = new JPanel();
	
	
	JPanel walletPanel = new JPanel();// 보유금액 송출
	JLabel walletLabel = new JLabel("보유금액 : ");
	JTextField typeWallet = new JTextField(); // 출력 창
	
	
	// 보유 주식 리스트
	JPanel havListPanel = new JPanel(new BorderLayout());// 보유 주식 송출
	JLabel havLabel = new JLabel("보유 주식 목록");
	JList havStockList = new JList();
	
	JPanel favlist = new JPanel();// 즐겨찾기 모델
	JComboBox<String> favComboBox = new JComboBox<>(); // 즐겨찾기
		
	
	
	ReceivedMSGTokenizer rt = new ReceivedMSGTokenizer();
	Connector connector;
	Client mainOperator = null;
	
	public MainFrame(Client _o) {
		mainOperator = _o;
		connector = _o.connector;
		// mainPanel 구성
		setTitle("가상 주식 어플리케이션");
		setSize(800, 500);
		// 창을 닫으면 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 메인화면을 중앙으로 배치
		setLocationRelativeTo(null);
		
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
		topPanel.add(searchPanel);
		
		// 공백 패널
		non.setPreferredSize(new Dimension(100, 30));
		topPanel.add(non);
		
		// 보유 금액 출력
		typeWallet.setPreferredSize(new Dimension(100, 30));
		typeWallet.setEditable(false);
		
		
		walletPanel.add(walletLabel);
		walletPanel.add(typeWallet);
		topPanel.add(walletPanel);
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		
		// 보유주식 조회 리스트
		havStockList.setPreferredSize(new Dimension(300, 350));
		havStockList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		havListPanel.add(havLabel, BorderLayout.NORTH);
		havListPanel.add(havStockList, BorderLayout.CENTER);
		
		// 즐겨찾기 콤보 박스
		favComboBox.setPreferredSize(new Dimension(200, 50));
		favlist.add(favComboBox);
		favlist.setPreferredSize(new Dimension(300, 350));
		
		non1.setPreferredSize(new Dimension(100, 30));
		
		centerPanel.add(havListPanel);
		centerPanel.add(non1);
		centerPanel.add(favlist);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		setContentPane(mainPanel);
		setVisible(false);
	}
	
	
	// 본인 지갑 최신화
	public void updateWallet() {
		_money = connector.sendWalletView(mainOperator.lf.getUserId());
		typeWallet.setText(String.valueOf(_money));
	}
	
	
	// 보유 주식 목록 최신화
	public void updateList() {
		ArrayList<Stock> list = new ArrayList<>();
		ArrayList<String> newlist = new ArrayList<>();
		
		String _msg;
		_msg = connector.sendHavList(mainOperator.lf.getUserId());
		list = rt.stockList(_msg);
		
		for(Stock s : list) {
			String stock = s.getItemName() + " | " + s.getHavCnt()+ "주" + " | " + s.getPrice() + " | " + s.getHavCnt()*s.getPrice();
			if(s.getHavCnt() != 0) {
				newlist.add(stock);
			}
		}
		
		havStockList.setListData(newlist.toArray(new String[0])); // AI 사용부분 : 정형화한 문자열을 어떻게 삽입해야 하는지 감을 잡지 못하여 사용하였습니다.
	}
	
	// 즐겨찾기 불러오기
	public void updatefavList() {
		favComboBox.removeAllItems();// 기존 항목 삭제 후 변경사항 적용
		ArrayList<Stock> list = new ArrayList<>();
		
		String _msg;
		_msg = connector.sendFavList(mainOperator.lf.getUserId());
		list = rt.favList(_msg);
		
		for(Stock s : list) {
			favComboBox.addItem(s.getItemName());
		}
		
	}
}
