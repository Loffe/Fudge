package se.eloff.fudge.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event used to indicate when a view need a refresh. A {@link RefreshEvent}
 * object has either a forum or a topic member that
 * 
 * @author eloff
 */
public class RefreshEvent extends GwtEvent<RefreshEventHandler> {
	public static Type<RefreshEventHandler> TYPE = new Type<RefreshEventHandler>();

	public RefreshEvent() {
	}

	@Override
	public Type<RefreshEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RefreshEventHandler handler) {
		handler.onRefresh(this);
	}
}
