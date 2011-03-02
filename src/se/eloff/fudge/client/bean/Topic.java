package se.eloff.fudge.client.bean;

import java.io.Serializable;

public class Topic implements Serializable {
	private static final long serialVersionUID = -8607386776696445688L;
	
	private int id;
	private int forumId;
	private String name;

	public Topic() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getForumId() {
		return forumId;
	}
}
