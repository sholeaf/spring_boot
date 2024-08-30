package com.example.demo.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component
//@Setter
@Data
//@Data : @Setter , @Getter, @ToString, ... 를 하나로 합친 어노테이션
public class Restaurant {
//	@Setter(onMethod_=@Autowired)
	@Autowired
	private Chef chef;
	
	
}
