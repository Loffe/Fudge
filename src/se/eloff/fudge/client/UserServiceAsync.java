package se.eloff.fudge.client;

import java.util.Map;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void createUser(User user, AsyncCallback<User> callback);

	void removeUser(User user, AsyncCallback<User> callback);

	void editUser(User user, AsyncCallback<User> callback);

	void getAllUsers(AsyncCallback<User[]> callback);

}
