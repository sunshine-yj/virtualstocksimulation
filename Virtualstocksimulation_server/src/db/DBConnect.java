//기말고사 대체 과제
//개발자 : 김유진
//개발기간 : 2025.12.02 ~ 2025.12.14
//내용 : DB를 MySQL과 연결하기 위한 클래스
package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	
	String address ="jdbc:mysql://127.0.0.1:3306/stockdb";
	String uid = "KYJ";
	String pwd = "password";
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	
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
