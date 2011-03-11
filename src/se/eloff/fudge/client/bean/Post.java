package se.eloff.fudge.client.bean;

import java.io.Serializable;
import java.sql.Date;

public class Post implements Serializable {
	private static final long serialVersionUID = 3211305636644559351L;
	
	private int id;
	private int topicId;
	private int userId;
	private Date postedOnDate;
	private String message;
	private User user;
	
	public Post() {
	}
	
	public Post(int id, int topicId, int userId, Date postedOnDate,
			String message) {
		this.id = id;
		this.topicId = topicId;
		this.userId = userId;
		this.postedOnDate = postedOnDate;
		this.message = message;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getPostedOnDate() {
		return postedOnDate;
	}

	public void setPostedOnDate(Date postedOnDate) {
		this.postedOnDate = postedOnDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCurrentTime() {
		java.util.Date date = new java.util.Date();
		setPostedOnDate(new java.sql.Date(date.getTime()));
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
