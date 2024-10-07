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
import com.example.demo.mapper.PLikelistMapper;
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
	
	@Autowired
	private PLikelistMapper plmapper;

	@Override
	public ArrayList<PBoardDTO> getList(Long lastBoardnum, int limit) {
		ArrayList<PBoardDTO> list = pbmapper.getList(lastBoardnum, limit);
		for(int i = 0; i < list.size(); i++) {
			String file = pfmapper.getImg(list.get(i).getBoardnum());
			Long like = plmapper.countlike(list.get(i).getBoardnum());
			list.get(i).setBoardlikecnt(like);
			list.get(i).setFile(file);
		}
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
				System.out.println(path);
				
				
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
		PBoardDTO boardDTO = pbmapper.getBoardByBoardnum(boardnum);
		boardDTO.setBoardlikecnt(plmapper.countlike(boardnum));
		return boardDTO;
	}

	@Override
	public boolean modify(PBoardDTO updateBoard) {
		return pbmapper.updateBoard(updateBoard);
	}

	@Override
	public Long getNextBoardnum(Long lastBoardnum) {
		return pbmapper.getNextBoardnum(lastBoardnum);
	}


	@Override
	public boolean remove(Long boardnum, String[] files) throws Exception {
		if(!pbmapper.delete(boardnum)) {
			return false;
		}
		if(!pfmapper.delete(boardnum)) {
			return false;
		}
		

		for (int i = 0; i < files.length; i++) {
			String systemname = files[i];

			String filePath = saveFolder + "pimages/" + systemname;
			File file = new File(filePath);

			// 파일이 존재하는지 체크
			if (file.exists()) {
				if (!file.delete()) {
					// 파일 삭제 실패
					System.err.println("Failed to delete file: " + filePath);
					return false; // 삭제 실패 시 false 반환
				}
			} else {
				// 파일이 존재하지 않을 경우 처리
				System.out.println("File does not exist: " + filePath);
			}
		}
		return true;
	}
}
