//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : DB를 MySQL과 연결하기 위한 클래스
package db;

import java.sql.*;

public class DBConnect {
	
	private static String address ="jdbc:mysql://127.0.0.1:3306/stockdb";
	private static String uid = "KYJ";
	private static String pwd = "password";
	private static String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	
	DBConnect() {
		try {
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e) {
			System.out.println("->JDBC Driver 오류");
			// e.printStackTrace();
		}
	}
	
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(address, uid, pwd);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
