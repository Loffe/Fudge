package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class TopicCanvas extends VStack {

	private PostServiceAsync svc;
	private final EventBus bus;
	private PostEditor postEditor;
	protected Topic currentTopic;

	public TopicCanvas(EventBus bus) {
		this.bus = bus;
		this.setWidth("80%");
	}

	private PostServiceAsync getService() {
		if (svc == null) {
			svc = (PostServiceAsync) GWT.create(PostService.class);
		}
		return svc;
	}

	public void showTopic(Topic topic) {
		currentTopic = topic;
		AsyncCallback<Post[]> callback = new AsyncCallback<Post[]>() {

			public void onFailure(Throwable caught) {
				System.out.println("Failed to get posts!");
			}

			public void onSuccess(Post[] result) {
				System.out.println("Successfully got posts");
				updateList(result);
			}
		};
		getService().getAllPosts(topic, callback);
	}

	protected void updateList(Post[] posts) {
		for (Canvas m : this.getMembers()) {
			this.removeMember(m);
		}
		System.out.println("update list of posts " + posts.length);

		for (Post p : posts) {
			this.addMember(createPostItem(p));
		}
		
		postEditor = new PostEditor() {
			@Override
			protected void onSubmit() {
				submitPost();
			}
		};
		this.addMember(postEditor);
	}

	protected void submitPost() {
		Post post = new Post();
		post.setMessage(postEditor.getMessage());
		post.setTopicId(currentTopic.getId());
		post.setCurrentTime();
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		};
		getService().createPost(post, callback);
	}

	protected Canvas createPostItem(final Post post) {
		VStack vstack = new VStack();
		
		HStack hstack = new HStack();
		hstack.setWidth100();
		hstack.setStyleName("post");


		DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		Label dateLabel = new Label(formatter.format(post.getPostedOnDate()));
		dateLabel.setStyleName("topicName");
		
		Label userLabel = new Label(String.valueOf(post.getUserId()));

		// TODO: add click handlers and number of replies

//		topiclabel.addClickHandler(new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				bus.fireEvent(new TopicEvent(post));
//			}
//		});

		hstack.addMember(dateLabel);
		hstack.addMember(userLabel);

		vstack.addMember(hstack);
		vstack.addMember(new Label(post.getMessage()));

		// hstack.addMember(new Label(String.valueOf(topic.getNrOfTopics())));

		return vstack;
	}
}
