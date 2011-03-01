package se.eloff.fudge.server;

import javax.servlet.http.HttpSession;

import se.eloff.fudge.client.LoginException;
import se.eloff.fudge.client.LoginService;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	interface UserAuthInterface {
		public User getLoggedInUser();

		public boolean validateUser(String username, String password);

	}

	private final UserAuthInterface userAuthInterface;
	private static final String USER_SESSION = "GWTAppUser";

	private static boolean fakeLoggedinUser = false;

	private static final long serialVersionUID = 1;

	public LoginServiceImpl() {
		userAuthInterface = new RealUserAuth();
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

		if (userAuthInterface.validateUser(username, password)) { // Check the
																	// database
			User user = new User();
			user.setUsername(username);
			setUserInSession(user);
			return user;
		} else
			throw new LoginException("Username and password does not match");

	}

	public User getLoggedInUser() {
		if (fakeLoggedinUser) {
			return userAuthInterface.getLoggedInUser();
		}
		return getUserFromSession();
	}

	public void logout() {
		setUserInSession(null);
	}

}