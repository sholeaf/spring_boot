package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.SampleDTO;

@Controller
public class SampleController {
//	@RequestMapping("/a")
	@GetMapping("/a")
//	문자열로 리턴을 하면 , View Resolver 설정에 맞는 해당 이름의 View를 찾는다.
//	prefix : /views/
//	suffix : .jsp
//	 /views/a.jsp
	public String a() {
		System.out.println("a 요청 호출!!");
		return "a";
	}
	
//	void 메소드는 , 요청명과 같은 이름의 View를 찾는다.
//	/views/basic.jsp
	@GetMapping("basic")
	public void asd() {
		System.out.println("basic 요청 호출!!");
	}
	
	@GetMapping("basic1")
	public void basicGet(int age) {
		System.out.println("basic1 요청 호출!! : "+ age);
	}
	
	@PostMapping("basic2")
	public void basicPost(int age) {
		System.out.println("basic2 요청 호출!! : "+ age);
	}
	
//	DTO 객체로 수집하기
	@GetMapping("test01")
	public void test01(SampleDTO dto){
		System.out.println(dto.getName());
		System.out.println(dto.getAge());
	}
	
//	파라미터 명과 담을 매개변수 명이 다른 경우 수집방법(특정 이름의 파라미터로 날아오는 데이터 수집 방법
	@GetMapping("test02")
	public void test02(@RequestParam("data2") String name, @RequestParam("data1") int age) {
		System.out.println(name);
		System.out.println(age);
	}
//	파라미터가 같은 이름으로 여러개 날아오는 경우 수집 방법
	@GetMapping("test03")
	public void test03(@RequestParam("data") String[] datas) {
		System.out.println(datas.length);
	}
	
	@GetMapping("test04")
	public void test04(@RequestParam("data") ArrayList<String> datas) {
		System.out.println(datas);
	}
}
