package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

import se.eloff.fudge.client.TopicService;
import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;
import se.eloff.fudge.client.bean.User;

public class TopicServiceImpl extends FudgeServiceServlet implements
		TopicService {

	private static final long serialVersionUID = -3939297794629957624L;

	public Topic createTopic(Topic topic, Post post) {
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		User user = getUserFromSession();
		if (user == null)
			System.out.println("User not logged in");

		post.setUserId(user.getId());

		try {
			return database.createTopic(conn, topic, post);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteTopic(Topic topic) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean editTopic(Topic Topic) {
		// TODO Auto-generated method stub
		return false;
	}

	public Topic[] getAllTopics(Forum forum) {
		DatabaseManager database = DatabaseManager.getInstance();
		Connection conn = database.getConnection();

		try {
			return database.getAllTopics(conn, forum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
