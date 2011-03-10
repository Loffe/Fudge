package se.eloff.fudge.client;

import java.util.Map;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {
	User[] getAllUsers();
	User createUser(User user);
	User removeUser(User user);
	User editUser(User user);
}
