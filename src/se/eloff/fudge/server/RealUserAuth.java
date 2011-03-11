package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

import se.eloff.fudge.client.LoginException;
import se.eloff.fudge.client.bean.User;

public class RealUserAuth {

	/**
	 * @param username
	 * @param password
	 * @return user if username and password is valid, otherwise null
	 * @throws LoginException 
	 */
	public User validateUser(String username, String password) throws LoginException {
		DatabaseManager db = DatabaseManager.getInstance();
		Connection conn = db.getConnection();
		try {
			return db.checkUserCredentials(conn, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new LoginException("Username and password does not match");
	}
}
