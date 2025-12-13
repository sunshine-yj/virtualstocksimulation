//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 메인 GUI
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
	JPanel centerPanel = new JPanel(new BorderLayout());
	JPanel centerRightPanel = new JPanel();
	JPanel bottomPanel = new JPanel(new FlowLayout());
	
	
	// 검색 패널
	JPanel searchPanel = new JPanel(new FlowLayout());// 검색기능
	JLabel searchLabel = new JLabel("주식명 : ");
	JTextField typeItemNm = new JTextField(); // 검색 창
	JButton search = new JButton("검 색");

	// 사이 공간 패널
	JPanel non = new JPanel();	
	
	// 지갑 송출
	JPanel walletPanel = new JPanel();// 보유금액 송출
	JLabel walletLabel = new JLabel("보유금액 : ");
	JTextField typeWallet = new JTextField(); // 출력 창
	
	
	// 인기 순위 리스트
	JPanel rankPanrl = new JPanel(new BorderLayout());
	JLabel rankLabel = new JLabel("주식 인기 순위");
	String rankHeader[] = {"순위", "종목명", "가격", "상승률", "거래량"};
	String[][] rankBody = new String[12][5];
	JTable rankTable;
	
	
	// 즐겨찾기
	JPanel favlist = new JPanel();// 즐겨찾기 모델
	JComboBox<String> favComboBox = new JComboBox<>(); // 즐겨찾기
	
	
	// 보유 주식 리스트(인기 순위와 변경해야 함)
	JPanel havListPanel = new JPanel(new BorderLayout());// 보유 주식 송출
	JLabel havLabel = new JLabel("보유 주식 목록");
	JList havStockList = new JList();
	String havHeader[] = {"종목명", "주식 수", "평균가", "총 가격"};
	String[][] havBody = new String[10][4];
	JTable havTable;
		
	
	ReceivedMSGTokenizer rt = new ReceivedMSGTokenizer();
	Connector connector;
	Client mainOperator = null;
	
	public MainFrame(Client _o) {
		mainOperator = _o;
		connector = _o.connector;
		// mainPanel 구성
		setTitle("가상 주식 어플리케이션");
		setSize(800, 400);
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
		
		
		//인기 차트
		rankPanrl.setPreferredSize(new Dimension(300, 250));
		rankPanrl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rankTable = new JTable(rankBody, rankHeader);
		rankPanrl.add(rankLabel, BorderLayout.NORTH);
		rankPanrl.add(new JScrollPane(rankTable), BorderLayout.CENTER);
		
		
		// 즐겨찾기 콤보 박스 + 선택시 검색
		favComboBox.setPreferredSize(new Dimension(200, 40));
		favComboBox.addActionListener(e -> {
			String itemName = (String) favComboBox.getSelectedItem();
			if(itemName.equals("선택목록")) {
				return;
			}
			String msg = connector.sendSearch(itemName);
			System.out.println("콤보 박스 : " + typeItemNm.getText());
			if(msg != null) {
				sif = new StockInfoFrame(mainOperator, msg, _money);
			} else {
				System.out.println("msg값이 없음");
			}
		});
		
		
		// 보유주식 조회 리스트
		havTable = new JTable(havBody, havHeader);
		havLabel.setHorizontalAlignment(SwingConstants.CENTER);
		havListPanel.add(havLabel, BorderLayout.NORTH);
		havListPanel.add(new JScrollPane(havTable), BorderLayout.CENTER);
		
		
		favlist.add(favComboBox);
		favlist.setPreferredSize(new Dimension(300, 75));
		
		
		centerRightPanel.setLayout(new BoxLayout(centerRightPanel, BoxLayout.Y_AXIS));
		centerRightPanel.add(favlist);
		centerRightPanel.add(havListPanel);
				
		centerPanel.add(rankPanrl, BorderLayout.WEST);
		centerPanel.add(centerRightPanel, BorderLayout.EAST);
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
		int i = 0;
		for(Stock s : list) {
			havBody[i][0] = s.getItemName();
			havBody[i][1] = String.valueOf(s.getHavCnt());
			havBody[i][2] = String.valueOf(s.getPrice());
			havBody[i][3] = String.valueOf(s.getHavCnt()*s.getPrice());
			i++;
		}
		
	}
	
	// 즐겨찾기 불러오기
	public void updatefavList() {
		favComboBox.removeAllItems();// 기존 항목 삭제 후 변경사항 적용
		ArrayList<Stock> list = new ArrayList<>();
		
		String _msg;
		_msg = connector.sendFavList(mainOperator.lf.getUserId());
		list = rt.favList(_msg);
		
		favComboBox.addItem("선택목록");
		for(Stock s : list) {
			favComboBox.addItem(s.getItemName());
		}
		
	}
	
	public void updateRankList() {
		ArrayList<Stock> list = new ArrayList<>();
		ArrayList<String> newlist = new ArrayList<>();
		String _msg;
		_msg = connector.sendRankList();
		list = rt.rankList(_msg);
		int i = 0;
		for(Stock s : list) {
			rankBody[i][0] = String.valueOf(i+1);
			rankBody[i][1] = s.getItemName();
			rankBody[i][2] = String.valueOf(s.getPrice());
			rankBody[i][3] = String.valueOf(s.getFltRt());
			rankBody[i][4] = String.valueOf(s.getTrqu());
			i++;
		}
		
	}
}
