package com.fastcampus.ch4.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	
	@Autowired
	BoardService boardService;
	
	//게시물 수정하기 
	@PostMapping("/modify")
	public String modify(BoardDto boardDto ,Integer page, Integer pageSize, Model m , HttpSession session, RedirectAttributes rattr) {
	
		String writer= (String)session.getAttribute("id");
		boardDto.setWriter(writer);
		
		
		try {
			
			int rowCnt = boardService.modify(boardDto);
			
			
			if (boardService.modify(boardDto)!= 1)
                throw new Exception("Modify failed.");
			
			rattr.addAttribute("page", page);
            rattr.addAttribute("pageSize", pageSize);
            rattr.addFlashAttribute("msg", "MOD_OK");
			
			return "redirect:/board/list";
			
		} catch (Exception e) {
			
			  e.printStackTrace();
	            m.addAttribute(boardDto);
	            m.addAttribute("page", page);
	            m.addAttribute("pageSize", pageSize);
	            m.addAttribute("msg", "MOD_ERR");
	            return "board"; // 등록하려던 내용을 보여줘야 함.
		}
		
	}
	
	
	// 게시물 수정하기 눌렀을때 화면 전환 
	@PostMapping("/write") // insert니까 delete인 remove하고 동일
    public String write(BoardDto boardDto, RedirectAttributes rattr, Model m, HttpSession session) {
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            if (boardService.write(boardDto) != 1)
                throw new Exception("Write failed.");

            rattr.addFlashAttribute("msg", "WRT_OK");
            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("mode", "new"); // 글쓰기 모드로 
            m.addAttribute(boardDto);      // 등록하려던 내용을 보여줘야 함.
            m.addAttribute("msg", "WRT_ERR");
            return "board"; 
        }
    }
	
	
	// 게시물 새로 쓰기 
	@GetMapping("/write")
	public String write(Model m) {
		m.addAttribute("mode", "new");
		return "board";
	}
	
	
	
	// 게시물 삭제하기 
	@PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, RedirectAttributes rattr, HttpSession session) {
        String writer = (String)session.getAttribute("id");
        String msg = "DEL_OK";

        try {
            if(boardService.remove(bno, writer)!=1)
                throw new Exception("Delete failed.");
        } catch (Exception e) {
            e.printStackTrace();
            msg = "DEL_ERR";
        }

        rattr.addAttribute("page", page);
        rattr.addAttribute("pageSize", pageSize);
        rattr.addFlashAttribute("msg", msg);
        return "redirect:/board/list";
    }
	
	
	// 게시물 읽기 
    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, RedirectAttributes rattr, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);
            m.addAttribute(boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addAttribute("page", page);
            rattr.addAttribute("pageSize", pageSize);
            rattr.addFlashAttribute("msg", "READ_ERR");
            return "redirect:/board/list";
        }

        return "board";
    }
	
	
    // 게시물 리스트 
    @GetMapping("/list")
    public String list(SearchCondition sc,Model m,  HttpServletRequest request) {
        if(!loginCheck(request))// 로그인을 안했으면 로그인 화면으로 이동
        	//return "redirect:/login/login";
            return "redirect:/login/login?toURL="+request.getRequestURL(); // 로그인을 안했으면 로그인 화면으로 이동

        try {
            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            /*
            if(page < 0 || page > pageHandler.getTotalPage())
                page = 1;
            if(pageSize < 0 || pageSize > 50)
                pageSize = 10;

            Map map = new HashMap();
            map.put("offset", (page-1)*pageSize);
            map.put("pageSize", pageSize);

            */
            
            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

	private boolean loginCheck(HttpServletRequest request) {
		
		// 세션을 얻어서 
		HttpSession session = request.getSession();
		
		// 세션에 id가 있는지 확인 / 있으면 true를 반환한다. 
		return session.getAttribute("id")!=null;
	}
}
