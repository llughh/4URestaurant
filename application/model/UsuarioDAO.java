package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.bbdd.ConnectionBBDD;

public class UsuarioDAO {
	
	public UsuarioDAO() {
		
	}
	
	public boolean login(String username, String password) {
		ConnectionBBDD conn = new ConnectionBBDD();
		
		try {
			ResultSet rs = conn.Consultar("SELECT * FROM user WHERE Username='" + username + "' AND Password='" + password + "'");
			if (rs.next()) {
				Usuario user = Usuario.getInstanceUser(
							rs.getInt("idUser"),
							rs.getString("Name"),
							rs.getString("Username"),
							rs.getString("Password"),
							rs.getInt("Rol"),
							rs.getString("Email"),
							rs.getString("Address")
						);
				return true;
			}
			else{
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
