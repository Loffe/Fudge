package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	private final User user;

	public LoginEvent(User user) {
		this.user = user;
	}
	
	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);	
	}

	public User getUser() {
		return user;
	}
}