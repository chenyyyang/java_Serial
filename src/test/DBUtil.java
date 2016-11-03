package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtil {
	private final static String URL = "jdbc:mysql://localhost:3306/test";
	private final static String USER_NAME = "root";
	private final static String PASSWORD = "123456";

	/**
	 * 获取数据库连接对象
	 * 
	 * @return
	 * @throws Exception 
	 * @throws ConnectionException
	 */
	public static Connection getConnection() throws Exception  {
		return getConnectionCommon();
	}

	private static Connection getConnectionCommon() throws Exception  {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// 不管方法内部出现什么问题，对于外部来说，就是连接出了问题
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			// 不管方法内部出现什么问题，对于外部来说，就是连接出了问题
			throw new Exception();
		}
		return connection;
	}

	/**
	 * 关闭相关的资源
	 * 
	 * @param rs
	 * @param stmt
	 * @param connection
	 */
	public static void close(ResultSet rs, Statement stmt, Connection connection) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
