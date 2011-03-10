package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class TopicCanvas extends ItemCanvas<Topic, Post> {

	private PostServiceAsync svc;
	private final EventBus bus;
	private PostEditor postEditor;

	public TopicCanvas(EventBus bus) {
		this.bus = bus;
		this.setWidth("80%");
		
		postEditor = new PostEditor() {
			@Override
			protected void onSubmit() {
				submitPost();
			}
		};
		this.addMember(postEditor);
	}

	private PostServiceAsync getService() {
		if (svc == null) {
			svc = (PostServiceAsync) GWT.create(PostService.class);
		}
		return svc;
	}
	
	@Override
	void showItem(Topic topic) {
		super.showItem(topic);
		getService().getAllPosts(topic, updateCallback);
	}

	protected void submitPost() {
		final Post post = new Post();
		post.setMessage(postEditor.getMessage());
		post.setTopicId(currentContainer.getId());
		post.setCurrentTime();
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				appendItem(post);
				postEditor.setMessage("");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		};
		getService().createPost(post, callback);
	}

	protected Canvas createItem(final Post post) {
		Layout hstack = new HLayout();
		hstack.setPadding(5);
		hstack.setShowEdges(true);
		hstack.setWidth100();
		hstack.setAutoHeight();
		hstack.setStyleName("post");
		
		Layout vstack = new VLayout();

		DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		Canvas dateLabel = new HTMLFlow(formatter.format(post.getPostedOnDate()));
		dateLabel.setStyleName("date");
		
		Canvas message = new HTMLFlow(post.getMessage());
		message.setStyleName("message");
		
		Layout userStack = new VLayout();
		userStack.setWidth(100);
		userStack.setStyleName("user");
		
		// TODO: add user name and email to post
		Canvas userLabel = new HTMLFlow(String.valueOf(post.getUserId()));
		
		// TODO: create hash from user email
		String hash = "475e55fc4e351736ee34946621bedd80";
		String url = "http://www.gravatar.com/avatar/" + hash + ".png";
		Canvas gravatar = new Img(url, 80, 80);

		vstack.addMember(dateLabel);		
		vstack.addMember(message);
		
		userStack.addMember(userLabel);
		userStack.addMember(gravatar);
		
		hstack.addMember(vstack);
		hstack.addMember(userStack);

		return hstack;
	}
}
