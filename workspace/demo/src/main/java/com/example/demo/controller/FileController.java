package com.example.demo.controller;

import java.net.URLEncoder;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.FileService;

@RequestMapping("/file/*")
@Controller
public class FileController {
	@Autowired
	private FileService service;
	
	@GetMapping("thumbnail")
	public ResponseEntity<Resource> thumbnail(String systemname) throws Exception{
		HashMap<String, Object> datas = service.getTumbnailResource(systemname);
		String contentType = (String)datas.get("contentType");
		Resource resource = (Resource)datas.get("resource");
		//응답 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
	@GetMapping("download")
	public ResponseEntity<Resource> download(String systemname, String orgname) throws Exception{
		Resource resource = service.downloadFile(systemname,orgname);
		HttpHeaders headers = new HttpHeaders();
		String dwName = "";
		dwName = URLEncoder.encode(orgname,"UTF-8").replaceAll("\\+", "%20");
		headers.setContentDisposition(ContentDisposition.builder("attachment").filename(dwName).build());
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
}














