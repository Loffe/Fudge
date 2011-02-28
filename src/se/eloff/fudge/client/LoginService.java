package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	public User checkLogin(String userName, String password) throws LoginException;
	public User getLoggedInUser();

}