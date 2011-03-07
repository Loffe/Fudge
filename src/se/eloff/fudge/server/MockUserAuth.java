package se.eloff.fudge.server;

import se.eloff.fudge.client.bean.User;
import se.eloff.fudge.server.LoginServiceImpl.UserAuthInterface;

public class MockUserAuth implements UserAuthInterface {

	public User getLoggedInUser() {
		return new User("gwt_test", "123", true, true);
	}

	public boolean validateUser(String username, String password) {
		return true;
	}

}
