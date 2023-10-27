package com.fastcampus.ch4.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fastcampus.ch4.dao.BoardDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class PageHandlerTest {

	
	 @Autowired
	    private BoardDao boardDao;
	
	@Test
	public void test1() {
			
		PageHandler ph = new PageHandler(250,1);
		ph.print();
		System.out.println("ph는 "+ ph);
		assertTrue(ph.getBeginPage() == 1);
		assertTrue(ph.getEndPage() == 10);
			
	}
		
	@Test
	public void test2() {
		
		PageHandler ph = new PageHandler(250,11);
		ph.print();
		System.out.println("ph는 "+ ph);
		assertTrue(ph.getBeginPage() == 11);
		assertTrue(ph.getEndPage() == 20);
		
	}
	
	@Test
	public void test3() {
		
		PageHandler ph = new PageHandler(255,25);
		ph.print();
		System.out.println("ph는 "+ ph);
		assertTrue(ph.getBeginPage() == 21);
		assertTrue(ph.getEndPage() == 26);
		
	}

}
