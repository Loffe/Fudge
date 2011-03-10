package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class ForumCanvas extends ItemCanvas<Forum, Topic> {

	private TopicServiceAsync svc;
	private final EventBus bus;

	public ForumCanvas(EventBus bus) {
		this.bus = bus;
	}

	private TopicServiceAsync getService() {
		if (svc == null) {
			svc = (TopicServiceAsync) GWT.create(TopicService.class);
		}
		return svc;
	}

	public void showForum(Forum forum) {
		super.showItem(forum);
		getService().getAllTopics(forum, updateCallback);
	}

	@Override
	protected Canvas createItem(final Topic topic) {
		HStack hstack = new HStack();
		hstack.setShowEdges(true);
		hstack.setStyleName("topic");

		VStack vstack = new VStack();
		Label topiclabel = new Label(topic.getName());
		topiclabel.setStyleName("topicName");
		topiclabel.setWidth(400);
		topiclabel.setHeight(40);
		topiclabel.setCursor(Cursor.HAND);

		// TODO: add click handlers and number of replies

		topiclabel.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				bus.fireEvent(new TopicEvent(topic));
			}
		});

		vstack.addMember(topiclabel);
		vstack.addMember(new Label(topic.getPost()));

		hstack.addMember(vstack);
		// hstack.addMember(new Label(String.valueOf(topic.getNrOfTopics())));

		return hstack;
	}
}
