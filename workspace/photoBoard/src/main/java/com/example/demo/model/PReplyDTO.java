package com.example.demo.model;

import lombok.Data;

@Data
public class PReplyDTO {
	private long replynum;
	private String replycontent;
	private String regdate;
	private String updatedate;
	private String replyuserid;
	private long boardnum;
}
