package se.eloff.fudge.server;

import javax.servlet.http.HttpSession;

import se.eloff.fudge.client.LoginException;
import se.eloff.fudge.client.LoginService;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {



	private static final String USER_SESSION = "GWTAppUser";

	private static final long serialVersionUID = 1;

	private RealUserAuth auth;

	public LoginServiceImpl() {
		auth = new RealUserAuth();
	}

	private void setUserInSession(User user) {
		HttpSession session = getThreadLocalRequest().getSession();
		session.setAttribute(USER_SESSION, user);
	}

	private User getUserFromSession() {
		HttpSession session = getThreadLocalRequest().getSession();
		return (User) session.getAttribute(USER_SESSION);
	}

	public User checkLogin(String username, String password)
			throws LoginException {

		if (auth.validateUser(username, password)) { // Check the
																	// database
			User user = new User();
			user.setUsername(username);
			setUserInSession(user);
			return user;
		} else
			throw new LoginException("Username and password does not match");

	}

	public User getLoggedInUser() {

		return getUserFromSession();
	}

	public void logout() {
		setUserInSession(null);
	}

}