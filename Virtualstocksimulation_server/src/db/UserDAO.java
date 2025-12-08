//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : 유저 관련 DB를 관리하기 위한 클래스
package db;

import java.sql.*;

public class UserDAO {
	
	DBConnect dbc = new DBConnect();
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt= null;
	private ResultSet rs = null;
	
	// 로그인 확인 메소드
	public int loginUser(String _uid, String _pwd) { // 로그인에 맞게 수정
		int result = -1;
		conn = dbc.getConnection();
		String query = "select * from users where user_id = ? and pwd = ?";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, _uid);
			pstmt.setString(2, _pwd);
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				System.out.println("-> " + _uid + " 로그인에 성공함");
				result = 1;// 로그인 성공시 1 전송
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}
		
		return result;
	}
	
	// 보유 금액 출력
	public int walletUser(String _uid) { 
		int result = -1;
		conn = dbc.getConnection();
		String query = "select wallet from users where user_id = ?";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, _uid);
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}
		
		return result;
	}
	
	// 보유 금액 업데이트
	public void walletMinus(String _uid, int _money) { 
		
		conn = dbc.getConnection();
		String queryselect = "select wallet from users where user_id = ?";
		String queryupdate = "update users set wallet = ? where user_id = ?";
		
		try {
			
			pstmt = conn.prepareStatement(queryselect);
			pstmt.setString(1, _uid);
			rs = pstmt.executeQuery();
			rs.next();
			int wallet = rs.getInt("wallet");
			
			wallet -= _money;
			
			pstmt.close();
			
			pstmt = conn.prepareStatement(queryupdate);
			
			pstmt.setInt(1, wallet);
			pstmt.setString(2, _uid);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}

	}
	// 보유 금액 업데이트
	public void walletPlus(String _uid, int _money) { 
		
		conn = dbc.getConnection();
		String queryselect = "select wallet from users where user_id = ?";
		String queryupdate = "update users set wallet = ? where user_id = ?";
		
		try {
			
			pstmt = conn.prepareStatement(queryselect);
			pstmt.setString(1, _uid);
			rs = pstmt.executeQuery();
			rs.next();
			int wallet = rs.getInt("wallet");
			
			wallet += _money;
			
			pstmt.close();
			
			pstmt = conn.prepareStatement(queryupdate);
			
			pstmt.setInt(1, wallet);
			pstmt.setString(2, _uid);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeAll();
		}

	}
	
	// 회원가입 메소드
	public void insertUser(String _uid, String _pwd) {
		
		conn = dbc.getConnection();
		String query = "insert into users (user_id, pwd) values(?,?)";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, _uid);
			pstmt.setString(2, _pwd);
			
			int updated = pstmt.executeUpdate();
			if(updated == 1) {
				System.out.println("-> " + _uid + " 회원가입에 성공함");
			}
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
