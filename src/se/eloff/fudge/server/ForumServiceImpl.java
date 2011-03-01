package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

import se.eloff.fudge.client.ForumService;
import se.eloff.fudge.client.bean.Forum;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ForumServiceImpl extends RemoteServiceServlet implements
		ForumService {

	public boolean createForum(Forum forum) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteForum(Forum forum) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean editForum(Forum forum) {
		// TODO Auto-generated method stub
		return false;
	}

	public Forum[] getAllForums() {

		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			return database.getAllForums(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
