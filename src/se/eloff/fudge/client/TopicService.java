package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("topic")
public interface TopicService extends RemoteService {
	Topic[] getAllTopics(Forum forum);
	Topic createTopic(Topic topic, Post post);
	boolean deleteTopic(Topic topic);
	boolean editTopic(Topic Topic);
}
