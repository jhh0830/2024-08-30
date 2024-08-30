package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.SubwayService;
import com.kh.model.vo.Subway;
import com.kh.view.SubwayView;

public class SubwayController {
	
	/**
	 * 차량 추가 요청 시 실행할 메소드  
	 * @param subName
	 * @param subFirst
	 * @param subLast
	 * @param express
	 * @param subColor
	 * @param subLine
	 * @param subDate => 사용자가 차량 추가 요청 시 입력했던 값들
	 */
	public void insertSubway(String subName, 
							 String subFirst, 
							 String subLast, 
							 String express, 
							 String subColor, 
							 int subLine, 
							 String subDate) {
		
	
		// 1) 요청시 전달값들을 VO 객체에 담기 
		Subway s = new Subway(subName, subFirst, subLast, express, subColor, subLine, subDate);
		
		// 2) Service 로 전달값을 넘기면서 메소드 호출 후 결과받기 
		int result = new SubwayService().insertSubway(s);
		
		// 3) 결과에 따른 응답화면 지정 
		if(result>0) { // 성공했을 경우 (commit)
			new SubwayView().displaySuccess("차량 추가 성공");
		} else { // 실패했을 경우(rollback) 		
			new SubwayView().displayFail("차량 추가 실패");
		}
	} // insertSubway 메소드 끝 
	
	/**
	 * 차량 전체 조회 요청 처리용 메소드
	 */
	public void selectAll() {
		// 1) Service 로 전달값을 넘기면서 메소드 호출 후 결과 받기 
		// > 전체 조회 기능이기 때문에 "여러행 조회"
		ArrayList<Subway> list = new SubwayService().selectAll();
		
		// 2) 결과에 따른 응답화면 지정 
		if(list.isEmpty()) { // 조회 결과가 없을 경우
			new SubwayView().displayNodata("전체 조회 결과가 없습니다.");
		} else { // 조회결과가 있을 경우
			new SubwayView().displayList(list);
		}
	} // selectAll 메소드 끝 
	
	/**
	 * 급행차량 조회
	 * @param subno
	 */
	public void selectByExpress(int subno) {
			
		Subway s = new SubwayService().selectByExpress(subno);
			
		if (s == null) {
				
			new SubwayView().displayNodata(subno+ "번 차량은 급행이 아닙니다.");
		}else {
			new SubwayView().displayOne(s);
		}
			
	} // selectByExpress 메소드 끝
		
	/**
	 * 기관사명 조회
	 * @param keword
	 */
	public void selectBySubName(String keword) {
		
		ArrayList<Subway> list = new SubwayService().selectBySubName(keword);
		
		if(list.isEmpty()) { 
			new SubwayView().displayNodata(keword + "에 해당되는 기관사가 없습니다.");
			
		} else {
			
			new SubwayView().displayList(list);
		}
	} // selectBySubName 메소드 끝
	
	public void updateSub(int subLine, String subFirst, String subLast,
			  			  String subColor, String subName) {

		// 1) 요청 시 전달값들을 하나의 VO 로 가공하기
		Subway s = new Subway();
		
		
		s.setSubLine(subLine);
		s.setSubFirst(subFirst);
		s.setSubLast(subLast);
		s.setSubColor(subColor);
		s.setSubName(subName);
		// 2) Service 로 전달값을 넘기면서 호출 후 결과 받기
		int result = new SubwayService().updateSub(s);
		
		// 3)
		if(result > 0) {
			
			new SubwayView().displaySuccess("차량 정보 변경 성공");
			
		} else {
			
			new SubwayView().displayFail("차량 정보 변경 실패");
		}
	} // updateSub 메소드 영역 끝
		
	public void deleteSubway(int subNo) {
		
		// 1) 요청 시 전달값들을 하나의 VO 객체로 가공하기
		// > 전달값이 한 개 이므로 패스
		
		// 2 Service 로 전달값들을 넘기면서 호출 후 결과 받기
		int result = new SubwayService().deleteSubway(subNo);
		
		// 3) 결과에 따른 응답화면 지정
		
		if(result > 0) {
			
			new SubwayView().displaySuccess("노후 차량 수리중");
		
		} else {
			
			new SubwayView().displayFail("노후 차량 아님");
		
		}
		
	} // deleteSubWay 메소드 영역끝
		
	public void selectBySubFix(int subLine) {
		
		// 1) 요청 시 전달값들을 하나의 VO 객체로 가공하기
		// > 전달값이 한 개 이므로 패스
		
		// 2) Service 로 전달값들을 넘기면서 호출 후 결과 받기
		ArrayList<Subway> list = new SubwayService().selectBySubFix(subLine);
		
		// 3) 결과에 따른 응답화면 지정
		if(list.isEmpty()) { // 조회 결과가 없을 경우
			new	SubwayView().displayNodata("수리할 기간이 아닙니다.");
		} else { // 조회 결과가 있을 경우
			
			new SubwayView().displaySubList(subLine, list);
		}
	}
}
