package se.eloff.fudge.client.bean;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * Add this variable for serialization
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private boolean isAdmin;
	private boolean isMod;

	public User(String username, String password, boolean isAdmin, boolean isMod) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.isMod = isMod;
	}

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getAdminRights(){
		return isAdmin;
	}
	
	public void setModeratorTights(boolean isMod){
		this.isMod = isMod;
	}
	public boolean getModeratorRights(){
		return isMod;
	}

}