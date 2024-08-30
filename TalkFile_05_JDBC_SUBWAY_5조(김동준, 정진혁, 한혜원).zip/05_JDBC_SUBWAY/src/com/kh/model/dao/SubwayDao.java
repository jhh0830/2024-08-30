package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Subway;

public class SubwayDao {
	// 필드부 
	private Properties prop = new Properties();
	
	// 생성자부 
	// Service 단에서 매번 DAO 메소드 호출 시 Properties 객체에 파일로부터 읽어들인 쿼리문들이 다 들어갈 수 있도록 유도
	public SubwayDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	// > 기본생성자 내부에서도 공통코드작업 가능! 
	
	// 메소드부 
	public int insertSubway(Connection conn, Subway s) {
		// insert문 => int (처리된 행의 갯수) => 트랜잭션 처리 
		// 0) 필요한 변수들 먼저 셋팅 
		int result = 0; // 처리된 행의 갯수를 담아줄 변수
		PreparedStatement pstmt = null; // SQL문 실행 후 결과를 받기 위한 변수 
		// 실행할 SQL문(미완성된 형태로, 세미콜론X) 
		String sql = prop.getProperty("insertSubway");
		
		try {
			// 1-1) SQL문을 넘기면서 PreparedStatement 객체 생성 
			pstmt = conn.prepareStatement(sql);
			// 1-2) 미완성된 SQL문일 경우에 값 채워주기
			pstmt.setString(1, s.getSubName());
			pstmt.setString(2, s.getSubFirst());
			pstmt.setString(3, s.getSubLast());
			pstmt.setString(4, s.getExpress());
			pstmt.setString(5, s.getSubColor());
			pstmt.setInt(6, s.getSubLine());
			pstmt.setString(7, s.getSubDate());
			// 2, 3) SQL문 실행 후 결과 받기 
			// - 실행할 구문 종류 : insert 
			// - 호출할 메소드명 : executeUpdate
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4) 다 쓴 JDBC용 자원 반납(역순) 
			JDBCTemplate.close(pstmt);
		}
		// 5) Service 로 리턴 
		return result;
	}
	
	public ArrayList<Subway> selectAll(Connection conn) {
		// 0) 필요한 변수들 먼저 셋팅 
		ArrayList<Subway> list = new ArrayList<>();
		PreparedStatement pstmt = null; 
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Subway s = new Subway();
				s.setSubNo(rset.getInt("SUBNO"));
				s.setSubName(rset.getString("SUBNAME"));
				s.setSubFirst(rset.getString("SUBFIRST"));
				s.setSubLast(rset.getString("SUBLAST"));
				s.setExpress(rset.getString("EXPRESS"));
				s.setSubColor(rset.getString("SUBCOLOR"));
				s.setSubLine(rset.getInt("SUBLINE"));
				s.setSubDate(rset.getString("SUBDATE"));
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}
	
	public Subway selectByExpress(Connection conn , int subno) {
		// 단일행 조회 ResultSet 객체 필요
		// 변수 셋팅
		Subway s = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// 실행할 SQL 문
		String sql = prop.getProperty("selectByExpress");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, subno);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				// 조회된 데이터를 기준으로
				// 한행 데이터 Subway 객체로 옮기기
				s = new Subway(rset.getInt("SUBNO"),
							   rset.getString("SUBNAME"),
							   rset.getString("SUBFIRST"),
							   rset.getString("SUBLAST"),
							   rset.getString("EXPRESS"),
							   rset.getString("SUBCOLOR"),
							   rset.getInt("SUBLINE"),
							   rset.getString("SUBDATE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return s;
	} // selectByExpress 메소드 끝
	
	public ArrayList<Subway> selectBySubName(Connection conn , String keyword){
		ArrayList<Subway> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		
		String sql = prop.getProperty("selectBySubName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Subway(rset.getInt("SUBNO"),
						   rset.getString("SUBNAME"),
						   rset.getString("SUBFIRST"),
						   rset.getString("SUBLAST"),
						   rset.getString("EXPRESS"),
						   rset.getString("SUBCOLOR"),
						   rset.getInt("SUBLINE"),
						   rset.getString("SUBDATE")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;	
	} // selectBySubName 메소드 끝
	
	public int updateSub(Connection conn, Subway s) {
		
		// 0) 필요한 변수들 셋팅
		int result = 0; // 처리된 행의 갯수 담아둘 변수
		PreparedStatement pstmt = null; // SQL 문 실행 후 결과를 받아낼 변수
		
		String sql = prop.getProperty("updateSub");
		try {
			
			// 3_1) SQL 문을 보내면서 PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3_2) 미완성된 SQL 문 완성
			pstmt.setString(1, s.getSubName());
			pstmt.setString(2, s.getSubFirst());
			pstmt.setString(3, s.getSubLast());
			pstmt.setString(4, s.getSubColor());
			pstmt.setInt(5, s.getSubLine());
			
			// 4, 5) SQL 문 실행 후 결과 받기
			// update문
			// executeUpdate	
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 반납 역순
			JDBCTemplate.close(pstmt);
		}
		// 8) Service 로 결과 리턴
		return result;
		
	} // updateSub 메소드 영여 끝
	
	public int deleteSubway(Connection conn, int subNo) {
		
		// 0) 필요한 변수 셋팅
		int result = 0; // 처리된 행의 갯수를 담을 변수
		PreparedStatement pstmt = null; // SQL 문 실행 후 결과를 받아낼 변수
		
		// 실행할 SQL 문
		String sql = prop.getProperty("deleteSubway");
		
		try {
			// 3_1) SQL 문을 전달하면서 PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3_2) 미완성된 SQL 문 완성
			pstmt.setInt(1, subNo);
			
			// 4, 5) SQL 문 실행 후 결과 받기
			// delete 문
			// executeUpdate
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 역순
			JDBCTemplate.close(pstmt);
					
		}
		
		// 8) Service 로 결과 리턴
		return result;
	} // delete 메소드 영역 끝
	
	public ArrayList<Subway> selectBySubFix(Connection conn, int subLine) {
		
		// 0) 필요한 변수 셋팅
		ArrayList<Subway> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectBySubFix");

		try {
			// 3_1) SQL 문을 전달하면서 PreaparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3_2) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, subLine);
			// 4, 5) SQL 문 실행 후 결과 받기
			// SELECT 문
			// executeQuery
			rset = pstmt.executeQuery();
			// 6_1) ResultSet 의 조회된 결과를 VO 로 옮겨담기
			while(rset.next()) {
				
				list.add(new Subway(rset.getInt("SUBNO")
							 , rset.getString("SUBNAME")
							 , rset.getString("SUBFIRST")
							 , rset.getString("SUBLAST")
							 , rset.getString("EXPRESS")
							 , rset.getString("SUBCOLOR")
							 , rset.getInt("SUBLINE")
							 , rset.getString("SUBDATE")));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 역순
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}


}
