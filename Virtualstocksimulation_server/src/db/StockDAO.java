//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 주식 관련 DB를 관리하기 위한 클래스
package db;

import java.sql.*;
import java.util.ArrayList;

public class StockDAO {
	DBConnect dbc = new DBConnect();
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt= null;
	private ResultSet rs = null;
	
	// 즐겨찾기 추가
	public void insertFav(String _uid, String _itemName) {
		
		conn = dbc.getConnection();
		String query = "insert into fav (user_id, itms_nm) values(?,?)";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, _uid);
			pstmt.setString(2, _itemName);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}
		
	}
	
	// 구매 주식 저장
	public void insertStock(String _uid, String _itemName, int _itemCnt, int _price) {
		
		conn = dbc.getConnection();
		String querySelect = "select havstockcnt, stock_price from havstock where itms_nm = ? and user_id = ?";
		String queryInsert = "insert into havstock (itms_nm, user_id, havstockcnt, stock_price) values(?,?,?,?)";
		String queryUpdate = "update havstock set  havstockcnt = ?, stock_price = ? where itms_nm = ? and user_id = ?";
		
		try {
			
			pstmt = conn.prepareStatement(querySelect);
			pstmt.setString(1, _itemName);
			pstmt.setString(2, _uid);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				
				int oldCnt = rs.getInt("havstockcnt");
				int oldPrice = rs.getInt("stock_price");
				
				pstmt.close();
				
				pstmt = conn.prepareStatement(queryUpdate);
				
				pstmt.setInt(1, _itemCnt + oldCnt);
				pstmt.setInt(2, (oldPrice * oldCnt + _itemCnt * _price) / (_itemCnt + oldCnt));
				pstmt.setString(3, _itemName);
				pstmt.setString(4, _uid);
				
				pstmt.executeUpdate();
				
			}
			else {
				
				pstmt.close();
				
				pstmt = conn.prepareStatement(queryInsert);
				pstmt.setString(1, _itemName);
				pstmt.setString(2, _uid);
				pstmt.setInt(3, _itemCnt);
				pstmt.setInt(4, _price);
				
				pstmt.executeUpdate();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}
	}
	
	// 판매 주식 저장
	public void sellStock(String _uid, String _itemName, int _itemCnt, int _price) {
		
		conn = dbc.getConnection();
		String querySelect = "select havstockcnt, stock_price from havstock where itms_nm = ? and user_id = ?";
		String queryUpdate = "update havstock set  havstockcnt = ? where itms_nm = ? and user_id = ?";
		
		try {
			
			pstmt = conn.prepareStatement(querySelect);
			pstmt.setString(1, _itemName);
			pstmt.setString(2, _uid);
			
			rs = pstmt.executeQuery();
			
			
			
			if(rs.next()) {
				
				int oldCnt = rs.getInt("havstockcnt");
				
				pstmt.close();
				
				pstmt = conn.prepareStatement(queryUpdate);
				
				pstmt.setInt(1, oldCnt - _itemCnt);
				pstmt.setString(2, _itemName);
				pstmt.setString(3, _uid);
				
				pstmt.executeUpdate();
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}
	}
	
	
	// 보유 주식 가져오기
	public int havStock(String _uid, String _itemName) {
		int result = -1;
		conn = dbc.getConnection();
		String query = "select havstockcnt from havstock where user_id = ? and itms_nm = ?";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, _uid);
			pstmt.setString(2, _itemName);
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				result = rs.getInt("havstockcnt");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}
		
		return result;
	}
	
	// 보유 주식 리스트 가져오기
	public ArrayList<Stock> havStockList(String _uid) {
		ArrayList<Stock> havStockList = new ArrayList<>();
		String item = null;
		int cnt = -1;
		int price = -1;
		
		
		conn = dbc.getConnection();
		String query = "select itms_nm, havstockcnt, stock_price from havstock where user_id = ?";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, _uid);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				item = rs.getString("itms_nm");
				cnt = rs.getInt("havstockcnt");
				price = rs.getInt("stock_price");
				
				havStockList.add(new Stock(item, cnt, price));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}
		
		return havStockList;
	}
	
	
	// 즐겨찾기 주식 리스트 가져오기
	public ArrayList<Stock> favStockList(String _uid) { 
		ArrayList<Stock> favStockList = new ArrayList<>();
		String item = null;
		
		conn = dbc.getConnection();
		String query = "select itms_nm from fav where user_id = ?";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, _uid);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				item = rs.getString("itms_nm");
				
				favStockList.add(new Stock(item));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}
		
		return favStockList;
	}
		
	
	// DB 연결 자원 해제
	private void removeAll() {
		try {
			if(rs != null) 
				rs.close();	
			if(stmt != null)
				stmt.close();
			if(pstmt != null)
				pstmt.close();
			if(conn != null)
				conn.close();
					
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
