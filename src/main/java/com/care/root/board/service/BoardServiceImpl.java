package com.care.root.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.care.root.board.dto.BoardDTO;
import com.care.root.board.dto.BoardRepDTO;
import com.care.root.mybatis.board.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired BoardMapper mapper;
	@Autowired BoardFileService bfs;
		
	public Map<String, Object> boardAllList(int num){
		int pageLetter = 3; //몇 개 글
		int allCount = mapper.selectBoardCount(); //글 총 개수
		int repeat = allCount / pageLetter;//총 페이지 수
		if(allCount % pageLetter != 0) {
			repeat++;
		}
		
		int end = num * pageLetter;
		int start = end + 1 - pageLetter;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repeat", repeat);
		map.put("list", mapper.boardAllList(start, end));
		
		
		return map;
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
		upHit(writeNo);
		return mapper.getContent(writeNo);
	}
	private void upHit(int writeNo) {
		mapper.upHit(writeNo);
	}
	
	public BoardDTO getContent(int writeNo) {
		return mapper.getContent(writeNo);
	}
	
	public String modify(BoardDTO dto, MultipartFile file) {
		
		String originName = null; 
		
		if( !file.isEmpty()) { //수정 됨
			originName = dto.getImageFileName();
			dto.setImageFileName(bfs.saveFile(file));
		}
		int result = mapper.modify(dto);
		String msg ="", url ="";
		if(result ==1) {
			//기존 이미지 삭제 originName
			bfs.deleteImage(originName);
			msg = "수정되었습니다~~";
			url="/root/board/contentView?writeNo="+dto.getWriteNo();
		}else {
			bfs.deleteImage(dto.getImageFileName());
			msg = "문제발생!!";
			url = "/root/board/modify_form?writeNo="+dto.getWriteNo();
		}
	
		return bfs.getMessage(msg, url);
		
	}
	
	public String delete (int writeNo, String fileName) {
		int result = mapper.delete(writeNo);
		String url="", msg="";
		if(result ==1) {
			msg="삭제완료";
			url="/root/board/boardAllList";
		}else {
			msg="문제발생";
			url="/root/board/contentView?writeNo="+writeNo;
		}
		return bfs.getMessage(msg, url);
	}
	
	public void addReply (BoardRepDTO dto) {
		mapper.addReply(dto);
	}
	
	public List<BoardRepDTO> getRepList(int write_group){
		return mapper.getRepList(write_group);
	}
}
