package com.example.demo.model.dto;

import lombok.Data;

@Data
public class BoardDTO {
	private int boardnum;
	private String boardtitle;
	private String boardcontents;
	private String regdate;
	private String userid;
}
