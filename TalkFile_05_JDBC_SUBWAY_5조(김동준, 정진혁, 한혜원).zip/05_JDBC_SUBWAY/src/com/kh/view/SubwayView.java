package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.SubwayController;
import com.kh.model.vo.Subway;

public class SubwayView {

	// Scanner 객체는 전역변수로 빼기 
	private Scanner sc = new Scanner(System.in);
	
	// SubwayController 객체 또한 전역변수로 빼기
	private SubwayController sbc = new SubwayController();
	
	/**
	 * 사용자가 보게 될 첫 화면 (메인화면) 
	 */
	public void mainMenu() {
		while(true) {
			System.out.println("***** 지하철 차량 관리 프로그램 *****");
			
			System.out.println("1. 차량 추가");
			System.out.println("2. 전체 차량 조회");
			System.out.println("3. 차량의 급행 여부 검색");
			System.out.println("4. 기관사 이름으로 차량 정보 검색");
			System.out.println("5. 차량 기관사 변경");
			System.out.println("6. 노후된 차량 수리");
			System.out.println("7. 차량 점검 우선순위");
			System.out.println("0. 프로그램 종료");
			
			System.out.println("------------------------------------------------");
			
			System.out.print("이용할 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1 : 
				insertSubway();
				break;
			case 2 : 
				selectAll();
				break;
			case 3 : 
				selectByExpress();
				break;
			case 4 : 
				selectBySubName();
				break; 
			case 5 : 
				updateSub();
				break; 
			case 6 : 
				deleteSubway();
				break; 
			case 7 : 
				selectBySubFix();
				break; 
			case 0 : 
				System.out.println("프로그램을 종료합니다.");
				return; 
			default : 
				System.out.println("번호를 잘못 입력했습니다. 다시 입력해주세요.");
			}
		}
	} // mainMenu 메소드 끝 
	
	/**
	 * 차량 추가용 화면 
	 */
	public void insertSubway() {
		System.out.println("--- 차량 추가 ---");
		
		// 차량 추가 요청 시 필요한 데이터
		// > *기관사이름, *첫차시간, *막차시간, 급행여부, 지하철 호선색, 지하철 호선, 차량 첫 운행날짜
		System.out.print("* 기관사 이름 : ");
		String subName = sc.nextLine(); 
		
		System.out.print("* 첫차시간 (HH24:MM): ");
		String subFirst = sc.nextLine();
		
		System.out.print("* 막차시간 (HH24:MM) : ");
		String subLast = sc.nextLine();
		
		System.out.print("급행여부 ('Y'/'N') : ");
		String express = sc.nextLine().toUpperCase();
		
		System.out.print("지하철 호선색 : ");
		String subColor = sc.nextLine();
		
		System.out.print("지하철 호선 (숫자만) : ");
		int subLine = sc.nextInt();
		sc.nextLine();
		
		System.out.print("차량 첫 운행 날짜 (RRRR/MM/DD) : ");
		String subDate = sc.nextLine();
		
		// Controller 의 어떤 메소드를 호출하면서 차량 추가 요청 
		// (위에서 입려받은 값들을 전달하면서) 
		sbc.insertSubway(subName, subFirst, subLast, express, subColor, subLine, subDate);
	} // insertSubway 메소드 끝 
	
	/**
	 * 회원 전체 조회용 화면
	 */
	public void selectAll() {
		System.out.println("--- 전체 차량 조회 ---");
		
		// Controller 로 전체 차량 조회 요청 
		sbc.selectAll();
	} // selectAll 메소드 끝
	
	public void selectByExpress() {
		System.out.println("--- 급행 여부 조회--- ");
		System.out.print("검색할 차량 번호 : ");
		int subno = sc.nextInt();
		sc.nextLine();
		
		sbc.selectByExpress(subno);
	} // selectByExpress 메소드 끝

	public void selectBySubName() {
		System.out.println("--- 차량 기관사 이름 키워드 검색---");
		System.out.print("검색할 기관사 이름 키워드 : ");
		String keyword = sc.nextLine();
		
		sbc.selectBySubName(keyword);
	} // selectBySubName 메소드 끝
	
	public void updateSub() {
		
		System.out.println("--- 차량 정보 변경 ---");
		
		System.out.print("수리할 차량의 노선 : ");
		int subLine = sc.nextInt();
		sc.nextLine();
				
		System.out.print("*변경할 첫차 시간 (HH24:MM) : ");
		String subFirst = sc.nextLine();
		
		System.out.print("*변경할 막차 시간 (HH24:MM) : ");
		String subLast = sc.nextLine();
		
		System.out.print("변경할 지하철 노선 색 : ");
		String subColor = sc.nextLine();		

		System.out.print("*변경할 차량의 기관사 이름 : ");
		String subName = sc.nextLine();
		
		// Controller 어떤 메소드를 호출하면서 변경 요청
		sbc.updateSub(subLine, subFirst, subLast, subColor, subName);
		
	} // updateSub 메소드 영역 끝
	
	public void deleteSubway() {
		
		System.out.println("--- 노후된 차량 수리 ---");
		
		// 수리 시킬 차량의 고유 번호 입력
		System.out.print("수리할 차량의 번호 : ");
		int subNo = sc.nextInt();
		sc.nextLine();
		
		//
		sbc.deleteSubway(subNo);
		
		
	} // deleteSubway 메소드 영역 끝
	
	public void selectBySubFix() {
		
		System.out.println("--- 수리 우선순위 ---");
		
		System.out.print("수리하고 싶은 차량의 호선 : ");
		int subLine = sc.nextInt();
		sc.nextLine();
		
		// Controller 의 어떤 메소드를 호출하면서 요청
		sbc.selectBySubFix(subLine);
		
	}
	
	// -----------------------------------------------------------------------------------------
	// 응답 시 보여줄 화면들 
	/**
	 * 요청 성공 시 보여질 화면
	 * @param message => 성공메시지 문구
	 */
	public void displaySuccess(String message) {
		System.out.println("서비스 요청 성공 : " + message);
	}
	
	/**
	 * 요청 실패 시 보여질 화면
	 * @param message => 실패메시지 문구
	 */
	public void displayFail(String message) {
		System.out.println("서비스 요청 실패 : " + message);
	}
	
	/**
	 * 전체 차량 조회 서비스 요청 시 조회 결과가 없을 경우 보여질 화면
	 * @param message => 메시지 내용
	 */
	public void displayNodata(String message) {
		System.out.println(message);
	}
	
	/**
	 * 전체 차량 조회 서비스 요청 시 여러 행이 조회된 결과를 받아서 보여줄 화면
	 * @param list => 여러 행이 조회된 결과
	 */
	public void displayList(ArrayList<Subway> list) {
		System.out.printf("조회된 차량 정보는 다음과 같습니다. (총 %d건)\n", list.size());
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	
	/**
	 * 급행차량일 때, 보여주는 화면
	 * @param s
	 */
	public void displayOne(Subway s) {
		
		System.out.println("이 차량은 급행입니다.");
		System.out.println(s);
	}
	
	public void displaySubList(int subLine, ArrayList<Subway> list) {
		
		System.out.printf("%d호선의 수리할 차량의 정보는 (총 %d건)\n", subLine,list.size());
		
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}


	
	
}
