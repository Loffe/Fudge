package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

public class RealUserAuth  {



	public boolean validateUser(String username, String password) {
		DatabaseManager db = DatabaseManager.getInstance();
		Connection conn = db.getConnection();
		try {
			return db.checkUserCredentials(conn, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
