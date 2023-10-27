package com.fastcampus.ch4.dao;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDaoImplTest2 {
	
	

    @Autowired
    BoardDao boardDao;

    @Test
    public void insertTestData() throws Exception { // 게시글 20개 생성 
    	boardDao.deleteAll();
    	
    	for(int i=1; i<=20; i++) {
    		BoardDto boardDto = new BoardDto("title"+i, "no content","작성자a");
    		boardDao.insert(boardDto);
    	}
    }
    
    @Test
    public void searchSelectPageTest() throws Exception {
    	
    	
    	SearchCondition sc = new SearchCondition(1,10,"title2","T");
    	List<BoardDto> list = boardDao.searchSelectPage(sc);
    	System.out.println("***************");
    	System.out.println(list);
    	System.out.println("***********");
    	assertTrue(list.size() ==2);
    	
    }
    
    @Test
    public void searchResultCntTest() throws Exception {
    	
    	
    	SearchCondition sc = new SearchCondition(1,10,"title2","T");
    	int cnt = boardDao.searchResultCnt(sc);
    	System.out.println("***************");
    	System.out.println(cnt);
    	System.out.println("***********");
    	assertTrue(cnt ==2);
    	
    }
    
    
    @Test
    public void insertTestData2() throws Exception { // 게시글 20개 생성 
    	boardDao.deleteAll();
    	
    	for(int i=1; i<=20; i++) {
    		BoardDto boardDto = new BoardDto("title"+i, "no content","작성자"+i);
    		boardDao.insert(boardDto);
    	}
    }
    
    @Test
    public void searchSelectPageTest2() throws Exception {
    	
    	
    	SearchCondition sc = new SearchCondition(1,10,"작성자2","W");
    	List<BoardDto> list = boardDao.searchSelectPage(sc);
    	System.out.println("***************");
    	System.out.println(list);
    	System.out.println("***********");
    	assertTrue(list.size() ==2);
    	
    }
    
    @Test
    public void searchResultCntTest2() throws Exception {
    	
    	
    	SearchCondition sc = new SearchCondition(1,10,"작성자2","W");
    	int cnt = boardDao.searchResultCnt(sc);
    	System.out.println("***************");
    	System.out.println(cnt);
    	System.out.println("***********");
    	assertTrue(cnt ==2);
    	
    }
   
    
}

