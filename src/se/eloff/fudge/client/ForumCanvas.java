package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ForumCanvas extends ItemCanvas<Forum, Topic> {

	private TopicServiceAsync svc;
	private Window createTopicDialog;
	protected PostEditor editor;

	public ForumCanvas(EventBus bus) {
		super(bus);
		Button createTopicButton = new Button("New Topic");
		this.addMember(createTopicButton, 0);
		createTopicButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showCreateTopicDialog();
			}
		});
	}

	private TopicServiceAsync getService() {
		if (svc == null) {
			svc = (TopicServiceAsync) GWT.create(TopicService.class);
		}
		return svc;
	}

	@Override
	public void showItem(Forum forum) {
		super.showItem(forum);
		getService().getAllTopics(forum, updateCallback);
	}

	@Override
	protected Canvas createItem(final Topic topic) {
		Post post = topic.getPost();
		if (post == null)
			throw new NullPointerException(
					"Topic without post cannot be added to canvas");

		Layout hstack = new HLayout();
		hstack.setPadding(5);
		hstack.setShowEdges(true);
		hstack.setWidth100();
		hstack.setAutoHeight();
		hstack.setStyleName("topic");
		hstack.setCursor(Cursor.HAND);

		// Left column
		Layout vstack = new VLayout();

		Canvas topiclabel = new HTMLFlow(topic.getName());
		topiclabel.setStyleName("topicName");

		Canvas message = new HTMLFlow(post.getMessage());
		message.setStyleName("message");

		// Right column
		Layout rightCol = new VLayout();
		rightCol.setWidth(200);

		DateTimeFormat formatter = DateTimeFormat
				.getFormat("yyyy-MM-dd HH:mm:ss");
		Canvas dateLabel = new HTMLFlow(formatter
				.format(post.getPostedOnDate()));
		dateLabel.setStyleName("date");

		Canvas nrOfReplies = new HTMLFlow("test");

		vstack.addMember(topiclabel);
		vstack.addMember(message);

		rightCol.addMember(dateLabel);
		rightCol.addMember(nrOfReplies);

		// TODO: add number of replies

		hstack.addMember(vstack);
		hstack.addMember(rightCol);

		hstack.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				bus.fireEvent(new TopicEvent(topic));
			}
		});

		return hstack;
	}

	protected void showCreateTopicDialog() {
		final Window win = getCreateTopicDialog();
		win.show();
	}

	protected Window getCreateTopicDialog() {
		if (createTopicDialog != null)
			return createTopicDialog;

		// Create the popup dialog box
		final Window dialogBox = new Window();
		dialogBox.setAutoCenter(true);
		dialogBox.setAutoSize(true);
		dialogBox.setTitle("New Topic");
		dialogBox.setIsModal(true);
		dialogBox.setShowModalMask(true);

		editor = new PostEditor(true) {

			@Override
			protected void onSubmit() {
				submitTopic();
			}
		};

		dialogBox.addItem(editor);

		createTopicDialog = dialogBox;
		return createTopicDialog;
	}

	protected void submitTopic() {
		final Topic topic = new Topic();
		topic.setName(editor.getTitle());
		topic.setForumId(currentContainer.getId());

		Post post = new Post();
		post.setMessage(editor.getMessage());
		post.setCurrentTime();

		AsyncCallback<Topic> callback = new AsyncCallback<Topic>() {
			@Override
			public void onSuccess(Topic result) {
				appendItem(result);
				editor.setMessage("");
				editor.setTitle("");
				getCreateTopicDialog().hide();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		};
		getService().createTopic(topic, post, callback);
	}
}
