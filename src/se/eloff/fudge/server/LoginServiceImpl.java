package se.eloff.fudge.server;

import javax.servlet.http.HttpSession;

import se.eloff.fudge.client.LoginException;
import se.eloff.fudge.client.LoginService;
import se.eloff.fudge.client.bean.User;

public class LoginServiceImpl extends FudgeServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1;

	private RealUserAuth auth;

	public LoginServiceImpl() {
		auth = new RealUserAuth();
	}

	private void setUserInSession(User user) {
		HttpSession session = getThreadLocalRequest().getSession();
		session.setAttribute(USER_SESSION, user);
	}

	public User checkLogin(String username, String password)
			throws LoginException {

		User user = auth.validateUser(username, password);
		System.out.println("I think your number is " + user.getId());
		user.setUsername(username);
		setUserInSession(user);
		return user;
	}

	public User getLoggedInUser() {

		return getUserFromSession();
	}

	public void logout() {
		setUserInSession(null);
	}

}