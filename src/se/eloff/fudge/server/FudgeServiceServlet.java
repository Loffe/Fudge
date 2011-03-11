package se.eloff.fudge.server;

import javax.servlet.http.HttpSession;

import se.eloff.fudge.client.LoginService;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class FudgeServiceServlet extends RemoteServiceServlet {

	protected static final String USER_SESSION = "GWTAppUser";

	protected User getUserFromSession() {
		HttpSession session = getThreadLocalRequest().getSession();
		return (User) session.getAttribute(USER_SESSION);
	}

}