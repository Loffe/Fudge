package se.eloff.fudge.client.bean;

import java.io.Serializable;

public class Topic implements Serializable {
	private static final long serialVersionUID = -8607386776696445688L;

	private int id;
	private int forumId;
	private String name;

	/**
	 * Post is not saved in the topics table. This member is used when joining
	 * with the first post in the topic.
	 */
	private String post;

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

	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * @return the first post's message in the topic.
	 */
	public String getPost() {
		return post;
	}
}
