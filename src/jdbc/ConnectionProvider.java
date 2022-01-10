package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	private static Connection conn=null;
	public static Connection getConnection() {
		try {
			String url="jdbc:oracle:thin:@localhost:1521:orcl";
			String id="jspid";
			String pw="jsppw";
			conn=DriverManager.getConnection(url,id,pw);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return conn;
	}
}
