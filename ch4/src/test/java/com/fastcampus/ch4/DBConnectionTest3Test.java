package com.fastcampus.ch4;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fastcampus.ch4.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest3Test {

	    @Autowired
	    DataSource ds;

	    @Test
		public void transctionTest() throws Exception{
			
			Connection conn= null;
	    	
			try {
	    		deleteAll();
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				
				String sql = "insert into user_info values (?,?,?,?,?,?,now())";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "id1111");
				pstmt.setString(2, "1111");
				pstmt.setString(3, "kim");
				pstmt.setString(4, "111@naver.com");
				pstmt.setDate(5, new java.sql.Date(new Date().getTime()));
				pstmt.setString(6, "sns1");
				
				int rowCnt = pstmt.executeUpdate();
				
				pstmt.setString(1, "id1");
				
				rowCnt = pstmt.executeUpdate();
				
				conn.commit();
				
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}

	       
	    }

	    private void deleteAll() throws Exception{
	    	
	    	Connection conn = ds.getConnection();
	    	
	        String sql = "delete from user_info";
	        
	        PreparedStatement pstmt =conn.prepareStatement(sql);
	        pstmt.executeUpdate();
		}
	    
	    
	    
	    
	    
	    //Test 항목들 
	    @Test
	    public void insertUserTest() throws Exception{
	    	
	    	User user = new User("id1","1111","kim","111@naver.com", new Date(),"sns1", new Date());
	    	int rowCnt = insertUser(user);
	    	
	    	assertTrue(rowCnt ==1);   	
	    }
	    
	    @Test
	    public void deleteUserTest() throws Exception {
	        deleteAll();
	        int rowCnt = deleteUser("id1");

	        assertTrue(rowCnt==0);

	        User user = new User("id1","1111","kim","111@naver.com", new Date(),"sns1", new Date());
	        rowCnt = insertUser(user);
	        assertTrue(rowCnt==1);

	        rowCnt = deleteUser(user.getId());
	        assertTrue(rowCnt==1);

	    }
	    
	    @Test
	    public void selectUserTest() throws Exception {
	        deleteAll();
	        User user = new User("id1","1111","kim","111@naver.com", new Date(),"sns1", new Date());
	        int rowCnt = insertUser(user);
	        User user2 = selectUser("id1");

	        assertTrue(user.getId().equals("id2"));
	    }
	    
	    
	    

	    public User selectUser(String id) throws Exception {
	        Connection conn = ds.getConnection();

	        String sql = "select * from user_info where id= ? ";

	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1,id);
	        ResultSet rs = pstmt.executeQuery();

	        if(rs.next()) {
	            User user = new User();
	            user.setId(rs.getString(1));
	            user.setPwd(rs.getString(2));
	            user.setName(rs.getString(3));
	            user.setEmail(rs.getString(4));
	            user.setBirth(new Date(rs.getDate(5).getTime()));
	            user.setSns(rs.getString(6));
	            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));
	            return user;
	        }
	        return null;
	    }

	    public int deleteUser(String id) throws Exception {
	        Connection conn = ds.getConnection();

	        String sql = "delete from user_info where id= ? ";

	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, id);

	        return pstmt.executeUpdate();
	    }
	    

	    public int updateUser(User user) throws Exception {

	        Connection conn = ds.getConnection();

	        String sql = "update user_info set pwd=? where id=? ";
	        
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, "9999");
	        pstmt.setString(2, user.getId());

	        int countSQL = pstmt.executeUpdate();
	        return countSQL;
	    }

	    
		public int insertUser(User user) throws Exception {
	    	
	    	Connection conn = ds.getConnection();
	        String sql = "insert into user_info values (?,?,?,?,?,?,now())";
	        
	        PreparedStatement pstmt =conn.prepareStatement(sql);
	        pstmt.setString(1, user.getId());
	        pstmt.setString(2, user.getPwd());
	        pstmt.setString(3, user.getName());
	        pstmt.setString(4, user.getEmail());
	        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
	        pstmt.setString(6, user.getSns());
	    
	        int rowCnt = pstmt.executeUpdate();

	        return rowCnt;
	    }
	}
