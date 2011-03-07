package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.eloff.fudge.client.bean.User;
import se.eloff.fudge.server.LoginServiceImpl.UserAuthInterface;

public class RealUserAuth implements UserAuthInterface {

	public User getLoggedInUser() {
		return new User("gwt_test", "123", true, true);
	}

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
