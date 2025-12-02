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
		
			while(rs.next()) {
				System.out.println("-> " + _uid + " 로그인에 성공함");
				result = 1;// 로그인 성공시 1 전송
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 회원가입 메소드 
	public boolean insertUser(String _uid, String _pwd) {
		
		boolean bool = false;
		
		conn = dbc.getConnection();
		String query = "insert into users values(?,?)";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, _uid);
			pstmt.setString(2, _pwd);
			
			int updated = pstmt.executeUpdate();
			if(updated == 1) {
				System.out.println("-> " + _uid + " 회원가입에 성공함");
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bool;
	}
}
