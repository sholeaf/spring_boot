package com.example.demo.model;

import lombok.Data;

@Data
public class PBoardDTO {
	private long boardnum;
	private String boardtitle;
	private String boardcontents;
	private long boardlikecnt;
	private String regdate;
	private String updatedate;
	private boolean boardflag;
	private String userid;
	
	private String file;
}
