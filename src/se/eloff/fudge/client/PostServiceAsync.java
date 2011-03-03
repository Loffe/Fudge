package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PostServiceAsync {

	void createPost(Post post, AsyncCallback<Boolean> callback);

	void deletePost(Post post, AsyncCallback<Boolean> callback);

	void editPost(Post post, AsyncCallback<Boolean> callback);

	void getAllPosts(Topic topic, AsyncCallback<Post[]> callback);

}
