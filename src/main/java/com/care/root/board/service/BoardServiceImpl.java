package com.care.root.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.care.root.board.dto.BoardDTO;
import com.care.root.mybatis.board.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired BoardMapper mapper;
	@Autowired BoardFileService bfs;
		
	public List<BoardDTO> boardAllList(){
		return mapper.boardAllList();
	}//boardAllList
	
	public String writeSave(BoardDTO dto, MultipartFile image_file_name) {
		
		if(image_file_name.isEmpty()) { //파일이 없다면
			dto.setImageFileName("nan");
		}else { //파일이 존재하는 경우
			dto.setImageFileName(bfs.saveFile(image_file_name) ); //특정위치에 파일 저장 + 파일 이름 만들기
		}
		
		int result = mapper.writeSave(dto);
		String msg = "", url="";
		
		if(result ==1) { //DB에 저장 성공
			msg = "새 글이 추가되었습니다.";
			url="/root/board/boardAllList";
			//root -> request.getContextPath()
		}else {//DB저장 실패
			msg = "문제 발생";
			url = "/root/board/writeForm";
		}
		return bfs.getMessage(msg,url);
	}//writeSave
	
	public BoardDTO contentView(int writeNo) {
		return mapper.getContent(writeNo);
	}
}
