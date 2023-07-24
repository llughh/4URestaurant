package application.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionBBDD {

	private Connection conn;
	private Statement st;
	
	public ConnectionBBDD() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197:3306/pri4URestaurant", "pri_4URestaurant", "4UR");
			st = conn.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ResultSet Consultar(String sql) throws SQLException {
		return st.executeQuery(sql);
	}
	
	public ResultSet SaveInfo(String sql) throws SQLException {
		return st.executeQuery(sql);
	}
	
	public void CloseConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
