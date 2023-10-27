package com.fastcampus.ch4;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

// maven MySQL 커넥션 연결방법 
public class DBConnectionTest {
    public static void main(String[] args) throws Exception {
        
       ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
       DataSource ds = ac.getBean(DataSource.class);
       
       Connection conn = ds.getConnection();
       System.out.println("******************");
       System.out.println("DB빈으로 연결 성공? "+ conn);
      
    } 
    
    
}

