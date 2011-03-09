package se.eloff.fudge.client;

import java.util.ArrayList;

import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class TopicCanvas extends VStack {

	private PostServiceAsync svc;
	private final EventBus bus;
	private PostEditor postEditor;
	protected Topic currentTopic;
	protected ArrayList<Post> currentPosts;

	public TopicCanvas(EventBus bus) {
		this.bus = bus;
		this.setWidth("80%");
		
		currentPosts = new ArrayList<Post>();
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
				currentPosts.clear();
				for (Post p : result) {
					currentPosts.add(p);
				}
				updateList(result);
			}
		};
		getService().getAllPosts(topic, callback);
	}

	protected void updateList(Post[] posts) {
		TopicCanvas.this.setAnimateMembers(false);
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
		final Post post = new Post();
		post.setMessage(postEditor.getMessage());
		post.setTopicId(currentTopic.getId());
		post.setCurrentTime();
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				int position = currentPosts.size();
				TopicCanvas.this.setAnimateMembers(true);
				TopicCanvas.this.addMember(createPostItem(post), position);
				currentPosts.add(post);
				postEditor.setMessage("");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		};
		getService().createPost(post, callback);
	}

	protected Canvas createPostItem(final Post post) {
		HLayout hstack = new HLayout();
		hstack.setPadding(5);
		hstack.setShowEdges(true);
		hstack.setWidth100();
		hstack.setAutoHeight();
		hstack.setStyleName("post");
		
		VLayout vstack = new VLayout();

		DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		Canvas dateLabel = new HTMLFlow(formatter.format(post.getPostedOnDate()));
		dateLabel.setStyleName("date");
		
		Canvas message = new HTMLFlow(post.getMessage());
		message.setStyleName("message");
		
		Canvas userLabel = new HTMLFlow(String.valueOf(post.getUserId()));
		userLabel.setStyleName("user");

		// TODO: add click handlers and number of replies

//		topiclabel.addClickHandler(new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				bus.fireEvent(new TopicEvent(post));
//			}
//		});

		vstack.addMember(dateLabel);		
		vstack.addMember(message);
		
		hstack.addMember(vstack);
		hstack.addMember(userLabel);

		// hstack.addMember(new Label(String.valueOf(topic.getNrOfTopics())));

		return hstack;
	}
}
