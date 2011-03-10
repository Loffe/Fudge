package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

import se.eloff.fudge.client.UserService;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements UserService{

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
	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(String user) {
		System.out.println("ZOMG deleting user! :: " + user);
		//TODO swap to real removal here instead of syso
		/*DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			return database.deleteUser(conn, user);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		return true;
	}

	@Override
	public boolean editUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setModerator(String user, Boolean isMod) {
		System.out.println("setting moderator status to: " + isMod + "on user: " + user);
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			return database.setModerator(conn, user, isMod);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}


}

