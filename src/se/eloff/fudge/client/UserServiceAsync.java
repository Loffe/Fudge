package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void createUser(User user, AsyncCallback<Boolean> callback);

	void deleteUser(User user, AsyncCallback<Boolean> callback);

	void editUser(User user, AsyncCallback<Boolean> callback);

	void getAllUsers(AsyncCallback<User[]> callback);

}
