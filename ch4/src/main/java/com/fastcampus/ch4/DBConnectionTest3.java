package com.fastcampus.ch4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.fastcampus.ch4.domain.User;


public class DBConnectionTest3 {
    @Autowired
    DataSource ds;
    
    //SQL문  
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
    
    private void deleteAll() throws Exception{
    	
    	Connection conn = ds.getConnection();
    	
        String sql = "delete form user_info";
        
        PreparedStatement pstmt =conn.prepareStatement(sql);
        pstmt.executeUpdate();
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