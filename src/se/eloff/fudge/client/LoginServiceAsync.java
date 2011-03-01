package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void checkLogin(String userName, String password,
			AsyncCallback<User> callback);

	void getLoggedInUser(AsyncCallback<User> callback);

	void logout(AsyncCallback<Void> callback);


}
