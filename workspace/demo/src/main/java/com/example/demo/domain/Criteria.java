package com.example.demo.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

//페이지를 구성하는 데이터들의 기준
@Data
public class Criteria {
	private int pagenum;
	private int amount;
	private String type;
	private String keyword;
	private int startrow;
	
	public Criteria() {
		//this() : 현재 클래스의 생성자
		this(1,10);
	}
	
	public Criteria(int pagenum, int amount) {
		this.pagenum = pagenum;
		this.amount = amount;
		this.startrow = (this.pagenum - 1) * this.amount;
	}
	
	
	//1. pagenum이 바뀔 때마다 startrow가 바뀌어야 함
	//2. pagenum이 바뀌려면 setPagenum() 호출해야 함
	//3. 결론 : setPagenum() 에서 startrow도 바꿔주어야 함
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
		this.startrow = (this.pagenum - 1) * this.amount;
	}
	
//	pagenum			listLink
//	getPagenum()	getListLink()
	public String getListLink() {
		//															? 앞에 붙는 URI 문자열
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
										.queryParam("pagenum", this.pagenum)
										.queryParam("amount", this.amount)
										.queryParam("type", this.type)
										.queryParam("keyword", this.keyword);
		//		?pagenum=4&amount=10& ...
		return builder.toUriString();
	}
}











