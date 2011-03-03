package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {
	User[] getAllUsers();
	boolean createUser(User user);
	boolean deleteUser(User user);
	boolean editUser(User user);
}
