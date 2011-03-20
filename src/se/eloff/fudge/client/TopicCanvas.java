package se.eloff.fudge.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class TopicCanvas extends ItemCanvas<Topic, Post> {

	private PostServiceAsync svc;
	private PostEditor postEditor;

	public TopicCanvas(EventBus bus) {

		super(bus);
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
		post.setUser(Fudge.user);
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				appendItem(post);
				postEditor.setMessage("");
				bus.fireEventFromSource(new RefreshEvent(), currentContainer);
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

		DateTimeFormat formatter = DateTimeFormat
				.getFormat("yyyy-MM-dd HH:mm:ss");
		Canvas dateLabel = new HTMLFlow(
				formatter.format(post.getPostedOnDate()));
		dateLabel.setStyleName("date");

		Canvas message = new HTMLFlow(post.getMessage());
		message.setStyleName("message");

		Layout userStack = new VLayout();
		userStack.setWidth(100);
		userStack.setStyleName("user");

		Canvas userLabel = new HTMLFlow(String.valueOf(post.getUserId()));
		User user = post.getUser();

		if (user != null) {
			userLabel = new HTMLFlow(user.getUsername());
		}

		String hashtext = null;
		String url = "http://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Leif_%22Loket%22_Olsson_-_frilagd.jpg/473px-Leif_%22Loket%22_Olsson_-_frilagd.jpg";

		if (user != null && user.getEmail() != null) {
			try {
				String plaintext = user.getEmail();
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.reset();
				m.update(plaintext.getBytes());
				byte[] digest = m.digest();
				BigInteger bigInt = new BigInteger(1, digest);
				hashtext = bigInt.toString(16);
				// Now we need to zero pad it if you actually want the full 32
				// chars.
				while (hashtext.length() < 32) {
					hashtext = "0" + hashtext;
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			url = "http://www.gravatar.com/avatar/" + hashtext + ".png";
		}
		Canvas gravatar = new Img(url, 80, 80);

		vstack.addMember(dateLabel);
		vstack.addMember(message);

		Button deleteButton = new Button("delete");
		deleteButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean result) {
						itemCanvas.setAnimateMembers(true);
						int position = currentItems.indexOf(post);
						currentItems.remove(post);
						itemCanvas.removeMember(itemCanvas.getMember(position));

						System.out.println("great succes");
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("great failure");
						// TODO Auto-generated method stub

					}
				};
				getService().deletePost(post, callback);

			}
		});

		if (Fudge.user.getModeratorRights())
			userStack.addMember(deleteButton);
		userStack.addMember(userLabel);
		userStack.addMember(gravatar);

		hstack.addMember(vstack);
		hstack.addMember(userStack);

		return hstack;
	}
}
