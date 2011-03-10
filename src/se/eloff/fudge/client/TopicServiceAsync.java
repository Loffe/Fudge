package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TopicServiceAsync {

	void getAllTopics(Forum forum, AsyncCallback<Topic[]> callback);

	void createTopic(Topic topic, Post post, AsyncCallback<Topic> callback);

	void deleteTopic(Topic topic, AsyncCallback<Boolean> callback);

	void editTopic(Topic Topic, AsyncCallback<Boolean> callback);

}
