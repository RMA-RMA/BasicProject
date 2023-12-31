package com.fastcampus.ch4.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;


@RestController
public class CommentController {

	@Autowired
	CommentService CommentService;
	
	
	//댓글을 수정 하는 메서드  
	@PatchMapping("/comments/{cno}")
	public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session) {
		
		// String commenter = (String)session.getAttribute("id);
		String commenter ="aaa"; // 임시 실행코드 작성자이
		dto.setCommenter(commenter);
	
		dto.setCno(cno);
		System.out.println("dto="+dto);
		
		try {
			
			
			if(CommentService.modify(dto) != 1)
				throw new Exception("modify Failed");
			
			return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
		}
	
	}
	
	//댓글을 등록하는 메서드  
	@PostMapping("/comments")
	public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
		
		// String commenter = (String)session.getAttribute("id);
		String commenter ="aaa"; // 임시 실행코드 작성자이
		dto.setCommenter(commenter);
		dto.setBno(bno);
		System.out.println("dto="+dto);
		
		try {
			if(CommentService.write(dto) != 1)
				throw new Exception("Write Failed");
			
			return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
		}
	
	}
	
	
	
	
	
	
	
	
	
	//지정된 댓글 삭제하는 메서드 
	@DeleteMapping("/comments/{cno}")
	public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
		
		// String commenter = (String)session.getAttribute("id);
		String commenter ="aaa"; // 임시 실행코드 작성자이
		
		try {
			int rowCnt = CommentService.remove(cno,bno,commenter);
			
			if(rowCnt != 1)
				throw new Exception("Delete Failed");
			
			return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
		}
	
	}
	

	
	@GetMapping("/comments")
	public ResponseEntity<List<CommentDto>> list(Integer bno) {
		
		
		List<CommentDto> list = null;
		
		try {
			list = CommentService.getList(bno);
			System.out.println("list="+ list);
			return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); // 성공 상태코드 200 
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 실패 상태코드 400 
		}
	}	
}
