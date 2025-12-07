//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 주식 관련 DB를 관리하기 위한 클래스
package db;

import java.sql.*;

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
