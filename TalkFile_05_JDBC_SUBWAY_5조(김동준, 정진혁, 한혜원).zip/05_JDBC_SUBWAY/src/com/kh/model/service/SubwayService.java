package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.SubwayDao;
import com.kh.model.vo.Subway;

public class SubwayService {

	/**
	 * 차량 추가 기능(트랜잭션)을 담당하는 메소드
	 * @param s => 추가할 차량의 정보들이 담겨있는 객체
	 * @return => 처리된 행의 갯수
	 */
	public int insertSubway(Subway s) {
		// 1) Connection 객체 생성 
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) Connection 객체와 전달값을 DAO로 넘기면서 메소드 호출 후 결과 받기 
		int result = new SubwayDao().insertSubway(conn, s);
		
		// 3) 트랜잭션 처리 
		if(result>0) { // 성공 
			JDBCTemplate.commit(conn);
		} else { // 실패 
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납 
		JDBCTemplate.close(conn);
		
		// 5) Controller로 결과 반환 
		return result;
	} // insertSubway 메소드 끝 
	
	public ArrayList<Subway> selectAll(){
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서 DAO의 메소드 호출 후 결과 받기 
		// > 여러행 조회 
		ArrayList<Subway> list = new SubwayDao().selectAll(conn);
		
		// 3) Connection 객체 반납 
		JDBCTemplate.close(conn);
		
		// 4) Controller 로 결과 리턴 
		return list; 
	} // selectAll 메소드 끝
	
	public Subway selectByExpress(int subno) {
		Connection conn = JDBCTemplate.getConnection();
		
		Subway s = new SubwayDao().selectByExpress(conn , subno);
		
		
		JDBCTemplate.close(conn);
		
		return s ;
				
	} // selectByExpress 메소드 끝 
	
	public ArrayList<Subway> selectBySubName(String keyword){
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Subway> list = new SubwayDao().selectBySubName(conn,keyword );
	
		JDBCTemplate.close(conn);
		
		return list;
		
	} // selectBySubName 메소드 끝
	
	public int updateSub(Subway s) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서 DAO 메소드 호출 후 결과 받기
		// 단일행 조회
		int result = new SubwayDao().updateSub(conn, s);
		
		// 3) 트랜잭션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) Controller 로 결과 리턴
		return result;
		
	} // updateSub 메소드 영역 끝
	
	public int deleteSubway(int subNo) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서 DAO 메소드 호출 후 결과 받기
		// 단일행 조회
		int result = new SubwayDao().deleteSubway(conn, subNo);
		
		// 3) 트랜잭션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) Controller 로 결과 리턴
		return result;
		
		
	} // deleteSubway 메소드 영역 끝
	
	public ArrayList<Subway> selectBySubFix(int subLine) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서 DAO 메소드 호출 후 결과 받기
		// 여러행 조회
		ArrayList<Subway> list = new SubwayDao().selectBySubFix(conn, subLine);
		
		// 3) 트랜잭션 처리
		// > SELECT 문 이므로 패스
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) Controller 로 결과 리턴
		return list;
		
		
		
		
	}

}
