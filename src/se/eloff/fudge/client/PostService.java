package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("post")
public interface PostService extends RemoteService {

	Post[] getAllPosts(Topic topic);

	boolean createPost(Post post);

	boolean deletePost(Post post);

	boolean editPost(Post post);
}
