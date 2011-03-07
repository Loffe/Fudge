package se.eloff.fudge.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class TopicCanvas extends VStack {

	private PostServiceAsync svc;
	private final EventBus bus;

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
	}

	protected Canvas createPostItem(final Post post) {
		HStack hstack = new HStack();
		hstack.setStyleName("topic");
		hstack.setWidth("80%");

		VStack vstack = new VStack();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Label postlabel = new Label(formatter.format(post.getPostedOnDate()));
		postlabel.setStyleName("topicName");
		postlabel.setWidth(400);
		postlabel.setHeight(40);

		// TODO: add click handlers and number of replies

//		topiclabel.addClickHandler(new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				bus.fireEvent(new TopicEvent(post));
//			}
//		});

		vstack.addMember(postlabel);
		vstack.addMember(new Label(post.getMessage()));

		hstack.addMember(vstack);
		// hstack.addMember(new Label(String.valueOf(topic.getNrOfTopics())));

		return hstack;
	}
}
