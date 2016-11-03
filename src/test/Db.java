package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Db{
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	

	public  boolean insertTem(String created,String tem) {
		int getback=0;
		String sql = "insert into t1103 (created,tem) values(?,?);";
		try {
			
			connection = DBUtil.getConnection();

			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,created);
			pstmt.setString(2,tem);
			
			getback= pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DBUtil.close(rs, pstmt, connection);
		}
		
		return true;
	}

	public static void main(String[] args) {
		Db db=new Db();
		db.insertTem("11-03", "12");
	}
	
}
