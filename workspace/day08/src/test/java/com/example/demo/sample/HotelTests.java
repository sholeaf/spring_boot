package com.example.demo.sample;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HotelTests {
	@Autowired
	private Hotel hotel;
	
	@Test
	public void testExist() {
//		System.out.println(hotel.getChef());
		//assert~~ : ~~이면 통과
		assertNotNull(hotel.getChef());
		
	}
}
