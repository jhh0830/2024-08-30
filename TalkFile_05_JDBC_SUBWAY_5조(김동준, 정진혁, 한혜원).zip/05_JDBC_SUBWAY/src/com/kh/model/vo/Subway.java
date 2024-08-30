package com.kh.model.vo;

public class Subway {

	// 필드부
	private int subNo; //	SUBNO NUMBER PRIMARY KEY, -- 차량 고유 번호
	private String subName; //    SUBNAME VARCHAR2(20) NOT NULL UNIQUE, -- 기관사 이름
	private String subFirst; //    SUBFIRST VARCHAR2(30) NOT NULL, -- 첫차 시간
	private String subLast; //    SUBLAST VARCHAR2(30) NOT NULL, -- 막차시간
	private String express; //    EXPRESS CHAR(1) DEFAULT 'N' CHECK(EXPRESS IN('Y', 'N')), -- 급행여부 Y N
	private String subColor; //    SUBCOLOR VARCHAR2(15), -- 지하철 호선색
	private int subLine; //    SUBLINE NUMBER, -- 지하철 호선
	private String subDate; //    SUBDATE DATE NOT NULL -- 차량 첫 운행 날짜
	
	// 생성자부 
	public Subway() {}

	public Subway(int subNo, String subName, String subFirst, String subLast, String express, 
				  String subColor, int subLine, String subDate) {
		super();
		this.subNo = subNo;
		this.subName = subName;
		this.subFirst = subFirst;
		this.subLast = subLast;
		this.express = express;
		this.subColor = subColor;
		this.subLine = subLine;
		this.subDate = subDate;
	}
	
	// 차량 추가용 생성자(매개변수 7개)
	public Subway(String subName, String subFirst, String subLast, String express, 
			      String subColor, int subLine, String subDate) {
		super();
		this.subName = subName;
		this.subFirst = subFirst;
		this.subLast = subLast;
		this.express = express;
		this.subColor = subColor;
		this.subLine = subLine;
		this.subDate = subDate;
	}

	// 메소드부 
	public int getSubNo() {
		return subNo;
	}

	public void setSubNo(int subNo) {
		this.subNo = subNo;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getSubFirst() {
		return subFirst;
	}

	public void setSubFirst(String subFirst) {
		this.subFirst = subFirst;
	}

	public String getSubLast() {
		return subLast;
	}

	public void setSubLast(String subLast) {
		this.subLast = subLast;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getSubColor() {
		return subColor;
	}

	public void setSubColor(String subColor) {
		this.subColor = subColor;
	}

	public int getSubLine() {
		return subLine;
	}

	public void setSubLine(int subLine) {
		this.subLine = subLine;
	}

	public String getSubDate() {
		return subDate;
	}

	public void setSubDate(String subDate) {
		this.subDate = subDate;
	}

	@Override
	public String toString() {
		return "Subway [차량고유번호=" + subNo + ", 기관사 이름=" + subName + ", 첫 차 시간=" + subFirst + ", 막 차 시간=" + subLast
				+ ", 급행여부=" + express + ", 지하철 호선 색=" + subColor + ", 지하철 호선=" + subLine + ", 첫 운행 날짜=" + subDate
				+ "]";
	}
}
