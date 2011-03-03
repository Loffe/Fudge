package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

import se.eloff.fudge.client.ForumService;
import se.eloff.fudge.client.UserService;
import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements UserService{

	public boolean createUser(Forum forum) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteUser(Forum forum) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean editUser(Forum forum) {
		// TODO Auto-generated method stub
		return false;
	}

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

	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean editUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}

