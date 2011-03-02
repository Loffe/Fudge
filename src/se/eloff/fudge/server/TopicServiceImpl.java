package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.SQLException;

import se.eloff.fudge.client.TopicService;
import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TopicServiceImpl extends RemoteServiceServlet implements TopicService {

	private static final long serialVersionUID = -3939297794629957624L;

	public boolean createTopic(Topic topic) {
		// TODO Auto-generated method stub
		return false;
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
