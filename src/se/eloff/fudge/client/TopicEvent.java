package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Topic;

import com.google.gwt.event.shared.GwtEvent;

public class TopicEvent extends GwtEvent<TopicEventHandler> {
	public static Type<TopicEventHandler> TYPE = new Type<TopicEventHandler>();
	private final Topic topic;

	public TopicEvent(Topic forum) {
		this.topic = forum;
	}
	
	@Override
	public Type<TopicEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TopicEventHandler handler) {
		handler.onShow(this);	
	}

	public Topic getTopic() {
		return topic;
	}
}
