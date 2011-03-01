package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.event.shared.GwtEvent;

public class ForumEvent extends GwtEvent<ForumEventHandler> {
	public static Type<ForumEventHandler> TYPE = new Type<ForumEventHandler>();
	private final Forum forum;

	public ForumEvent(Forum forum) {
		this.forum = forum;
	}
	
	@Override
	public Type<ForumEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ForumEventHandler handler) {
		handler.onShow(this);	
	}

	public Forum getForum() {
		return forum;
	}
}
