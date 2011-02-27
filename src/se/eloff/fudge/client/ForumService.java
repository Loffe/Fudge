package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("forum")
public interface ForumService extends RemoteService {
	Forum[] getAllForums();
	boolean createForum(Forum forum);
	boolean deleteForum(Forum forum);
	boolean editForum(Forum forum);
}
