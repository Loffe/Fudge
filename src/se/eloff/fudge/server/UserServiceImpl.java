package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

import se.eloff.fudge.client.UserService;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7977092421309094858L;

	public User[] getAllUsers() {
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			return database.getAllUsers(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User createUser(User user) {
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			database.createUser(conn, user);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		return user;
	}

	@Override
	public User removeUser(User user) {
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			return database.removeUser(conn, user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User editUser(User user) {
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();
		try {
			return database.editUser(conn, user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
