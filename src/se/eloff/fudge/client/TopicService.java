package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("topic")
public interface TopicService extends RemoteService {
	Topic[] getAllTopics(Forum forum);
	boolean createTopic(Topic topic);
	boolean deleteTopic(Topic topic);
	boolean editTopic(Topic Topic);
}
