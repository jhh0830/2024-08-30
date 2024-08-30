package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
 * * JDBCTemplate 클래스
 * JDBC 과정 중 반복적으로 쓰이는 구문들을 각각의 메소드 단위로 정의해둘 곳 
 * 해당 과정이 필요할 때마다 알맞은 메소드를 계속 호출해서 사용할 것! 
 * "재사용할 목적"으로 공통 템플릿 (공통 코드) 작업 진행 
 * 
 * => "재사용", "공유"의 개념이 강한 공통 코드를 작성 
 *    이 클래스에서의 모든 메소드들을 다 static 메소드로 작성해보자! 
 *    (싱글톤 패턴 적용)
 *
 * 싱글톤 패턴 : 메모리 영역에 단 한 번 올라간 것을 재사용 하는 개념
 * 
 * - 공통적으로 필요한 코드들 
 * 1) Connection 객체를 생성하는 코드 
 * 2) JDBC 용 객체를 반납하는 코드 
 * 3) Connection 객체를 가지고 트랜잭션 처리를 해주는 코드 
 */
public class JDBCTemplate {

	// 1) DB에 접속정보를 전달하면서 Connection 객체를 생성해서 반환해주는 메소드 
	public static Connection getConnection() {
		
		/*
		 * * 기존의 방식 
		 * JDBC Driver 구문, 접속할 DB의 url, 계정명, 비밀번호 등을
		 * 자바 소스코드 내에 직접 명시적으로 작성 => 정적코딩방식 (하드코딩) 
		 * - 문제점 : DBMS가 변경되었을 경우, 접속할 DB의 url 주소가 변경되었을 경우, 
		 * 			계정명, 비밀번호가 변경되었을 경우 => 자바 코드를 수정해야함! 
		 * > 코딩을 모르는 관리자 입장에서 값들을 수정하기가 매우 번거로워짐 
		 * 	 (관리자 입장에서 프로그램 유지보수가 귀찮아짐) 
		 * > 수정된 내용을 반영하고자 한다면 프로그램을 종료시켰다가 재구동 해야함! 
		 * 	 (사용자 입장에서 프로그램 사용 중 비정상적으로 종료되었다가 다시 구동될 수 있음) 
		 * 
		 * - 해결방법 : DB 관련된 정보들을 별도로 관리하는 외부 파일(.properties)로 만들어서 관리 
		 * 			 항상 DB에 접속할 때마다 외부파일로 읽어들여서 접속 정보를 코드에 반영시킬 것! => 동적코딩방식
		 * 
		 */
		// 변수 선언 및 null로 초기화
		Connection conn = null;
		
		Properties prop = new Properties(); // { }
		
		try {
			// resources/driver.properties 파일을 읽어들이기
			prop.load(new FileInputStream("resources/driver.properties"));
			// > 이 시점 기준으로 prop에는 키+밸류 세트로 DB 접속 정보들이 다 들어있을것! 
			
			// Connection 객체를 생성하려면 JDBC Driver 를 등록하는 과정을 먼저 거쳐야 함!
			Class.forName(prop.getProperty("driver"));
			
			// Connection 객체 생성하기 
			conn = DriverManager.getConnection(prop.getProperty("url"), 
											   prop.getProperty("username"), 
											   prop.getProperty("password"));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 만들어진 Connection 객체 리턴 
		return conn;
	} // getConnectino 메소드 끝 
	
	// 2) 전달받은 JDBC용 객체를 반납하는 메소드
	// 2-1) Connection 객체를 전달받아서 반납시켜주는 메소드
	public static void close(Connection conn) {
		try {
			if((conn != null) && (!conn.isClosed())) {
				// > 전달된 conn이 null이 아니고 그리고 전달된 conn이 close 된 상태가 "아니(!)"라면 
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close 메소드 끝 
	
	// 2-2) (Prepared)Statement 객체를 전달받아서 반납시켜주는 메소드
	// > Statement가 PreparedStatement 의 부모임! 
	// "다형성" 에 의해 부모타입 메소드만 한번 정의해두면 잘 돌아갈 것임!
	// 또한 메소드명이 같으나 매개변수의 타입이 다르기 때문에 "오버로딩"까지 적용됨 
	public static void close(Statement stmt) {
		try {
			if((stmt != null) && (!stmt.isClosed())){
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close 메소드 끝 
	
	// 2-3) ResultSet 객체를 전달받아서 반납시켜주는 메소드 
	public static void close(ResultSet rset) {
		try {
			if((rset != null) && (!rset.isClosed())) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close 메소드 끝 
	
	// 3) 전달받은 Connection 객체를 가지고 트랜잭션 처리를 해주는 메소드 
	// 3-1) 전달받은 Connection 객체를 가지고 COMMIT 시켜주는 메소드 
	public static void commit(Connection conn) {
		try {
			if((conn != null) && (!conn.isClosed())) {
				conn.commit();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // commit 메소드 끝 
	
	// 3-2) 전달받은 Connection 객체를 가지고 rollback 시켜주는 메소드 
	public static void rollback(Connection conn) {
		try {
			if((conn != null) && (!conn.isClosed())) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // rollback 메소드 끝
	
	
}
