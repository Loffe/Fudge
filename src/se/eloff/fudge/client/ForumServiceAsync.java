package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForumServiceAsync {

	void createForum(Forum forum, AsyncCallback<Boolean> callback);

	void deleteForum(Forum forum, AsyncCallback<Boolean> callback);

	void editForum(Forum forum, AsyncCallback<Boolean> callback);

	void getAllForums(AsyncCallback<Forum[]> callback);

}
