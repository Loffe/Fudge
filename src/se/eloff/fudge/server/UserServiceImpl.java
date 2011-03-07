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
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}


}

