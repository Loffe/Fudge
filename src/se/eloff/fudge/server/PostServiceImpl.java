package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

import se.eloff.fudge.client.PostService;
import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PostServiceImpl extends RemoteServiceServlet implements PostService {

	@Override
	public boolean createPost(Post post) {
		System.out.println("createPost");
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			database.createPost(conn, post);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deletePost(Post post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editPost(Post post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Post[] getAllPosts(Topic topic) {
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			return database.getAllPosts(conn, topic);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
