package se.eloff.fudge.server;

import se.eloff.fudge.client.ForumService;
import se.eloff.fudge.client.bean.Forum;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ForumServiceImpl extends RemoteServiceServlet implements
ForumService  {

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
		Forum[] allForums = new Forum[10];
		
		for	(int i = 0; i < 10; i++) {
			allForums[i] = new Forum("Forum number " + i, "a very very interesting and happy place");
			allForums[i].setNrOfTopics(i);
		}
		return allForums;
	}

}
