package com.example.demo.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.PBoardMapper;
import com.example.demo.mapper.PFileMapper;
import com.example.demo.model.PBoardDTO;
import com.example.demo.model.PFileDTO;

@Service
public class PBoardServiceImpl implements PBoardService {

	@Value("${file.dir}")
	private String saveFolder;

	@Autowired
	private PBoardMapper pbmapper;
	
	@Autowired
	private PFileMapper pfmapper;

	@Override
	public ArrayList<PBoardDTO> getList(Long lastBoardnum, int limit) {
		ArrayList<PBoardDTO> list = pbmapper.getList(lastBoardnum, limit);
		return list;
	}

	@Override
	public Long getStartnum() {
		return pbmapper.getBoardnum();
	}

	@Override
	public boolean regist(PBoardDTO pbdto, MultipartFile[] files) throws Exception {

		if (pbmapper.insertBoard(pbdto)) {
			long boardnum = pbmapper.getBoardnum();

			if (files == null || files.length == 0) {
				return false;
			}
			
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				String orgname = file.getOriginalFilename();
				int lastDot = orgname.lastIndexOf(".");

				String ext = orgname.substring(lastDot);

				LocalDateTime now = LocalDateTime.now();
				String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
				String systemname = time + UUID.randomUUID().toString() + ext;
				
				String path = saveFolder +"pimages/"+ systemname;
				
				PFileDTO pfdto = new PFileDTO();
				pfdto.setOrgname(orgname);
				pfdto.setSystemname(systemname);
				pfdto.setBoardnum(boardnum);
				if(pfmapper.insertFile(pfdto) < 0) {
					return false;
				}
				
				file.transferTo(new File(path));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public PBoardDTO getBoardByBoardnum(Long boardnum) {
		return pbmapper.getBoardByBoardnum(boardnum);
	}

}
