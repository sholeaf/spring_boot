package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TimeMapper {
	String getTime1();
	
	//Mapper를 작성하는 작업은 XML을 이용할 수도 있지만,
	//최소한의 코드로 작성하기 위해서는 Mapper 인터페이스에 바로 작성한다.
	@Select("select now() from dual")
	String getTime2();
}
