package se.eloff.fudge.server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Post;
import se.eloff.fudge.client.bean.Topic;
import se.eloff.fudge.client.bean.User;

public class DatabaseManager {
	protected static DatabaseManager instance;

	protected Connection connection;

	public DatabaseManager() {
	}

	public static DatabaseManager getInstance() {
		if (instance == null)
			instance = new DatabaseManager();
		return instance;
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			System.out.println("getConnection()");
			String filename = "fudge.db";
			String filePath = "/" + System.getProperty("user.dir") + filename;

			File f = new File(filePath);
			boolean createTable = !f.exists();

			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:/" + filePath);

			if (createTable) {
				createTables(conn);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void createTables(Connection conn) throws SQLException {
		System.out.println("Creating tables");
		createTableUsers(conn);

		createTableForums(conn);

		createTableTopics(conn);

		createTablePosts(conn);
	}

	private void createTableUsers(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists users;");

		stat.executeUpdate("create table users (uid integer primary key, "
				+ "name varchar(20) unique , password varchar(20), isAdmin integer, isMod integer, email varchar(20));");

		// Create default user
		PreparedStatement prep = conn
				.prepareStatement("insert into users values (?, ?, ?, ?, ?, ?);");
		prep.setInt(1, 1);
		prep.setString(2, "gwt");
		prep.setString(3, "password");
		prep.setInt(4, 1);
		prep.setInt(5, 1);
		prep.setString(6, "erik@eloff.se");
		prep.execute();
	}

	private void createTableForums(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists forums;");

		stat.executeUpdate("create table forums (fid integer primary key, "
				+ "name varchar(20), description varchar(200));");

		// Create default user
		PreparedStatement prep = conn
				.prepareStatement("insert into forums (name, description) "
						+ " values (?, ?);");
		for (int i = 1; i <= 10; i++) {
			prep.setString(1, "Forum number " + i);
			prep.setString(2, "a very very interesting and happy place");
			prep.execute();
		}
	}

	private void createTableTopics(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists topics;");

		stat.executeUpdate("create table topics (tid integer primary key, "
				+ "name varchar(20), fid integer);");

		// Create default topic
		PreparedStatement prep = conn
				.prepareStatement("insert into topics (tid, name, fid) "
						+ " values (?, ?, ?);");
		for (int i = 1; i <= 10; i++) {
			prep.setInt(1, i);
			prep.setString(2, "Topic number " + i);
			prep.setInt(3, 2 + i % 4);
			prep.execute();
		}
	}

	private void createTablePosts(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists posts;");

		stat.executeUpdate("create table posts (pid integer primary key, "
				+ "tid int, uid int, postedOnDate date, message text);");

		// Create default post
		PreparedStatement prep = conn
				.prepareStatement("insert into posts (pid, tid, uid, postedOnDate, message) "
						+ " values (?, ?, ?, ?, ?);");
		for (int i = 1; i <= 20; i++) {
			prep.setInt(1, i);
			prep.setInt(2, 1 + i % 10);
			prep.setInt(3, 0); // Fake user id
			// Get current time
			java.util.Date date = new java.util.Date();
			prep.setDate(4, new java.sql.Date(date.getTime()));
			prep.setString(5,
					"Hello forum. This is an awesome web application.\n "
							+ "Kudos to you developers :D");

			prep.execute();
		}
	}

	/**
	 * Check if a user with the given user name and password exists.
	 * 
	 * @param conn
	 * @param username
	 * @param password
	 * @return user if username and password is valid, otherwise null
	 * @throws SQLException
	 */
	public User checkUserCredentials(Connection conn, String username,
			String password) throws SQLException {
		PreparedStatement stat = conn
		// .prepareStatement("select uid, isAdmin, isMod from users where name = ? and password = ?");
				.prepareStatement("select uid, isMod, isAdmin, email from users where name = ? and password = ?");
		stat.setString(1, username);
		stat.setString(2, password);
		ResultSet rs = stat.executeQuery();

		User user = new User();
		user.setUsername(username);
		user.setEmail(rs.getString("email"));
		if (rs.getInt("isAdmin") == 1)
			user.setAdminRights(true);
		if (rs.getInt("isAdmin") == 0)
			user.setAdminRights(false);
		if (rs.getInt("isMod") == 1)
			user.setModeratorRights(true);
		if (rs.getInt("isMod") == 0)
			user.setModeratorRights(false);

		int numRows = 0;
		while (rs.next()) {
			++numRows;
			user.setId(rs.getInt("uid"));
			// user.setAdminRights(rs.getInt("isAdmin") == 1);
			// user.setModeratorRights(rs.getInt("isMod") == 1);
		}
		rs.close();

		if (numRows == 1) {
			// user exists
			return user;
		} else {
			return null;
		}
	}

	public Forum[] getAllForums(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("select forums.fid, forums.name, description, "
						+ "count(tid) as nrOfTopics " + "from forums "
						+ "left join topics on forums.fid = topics.fid "
						+ "group by forums.fid");
		ArrayList<Forum> forums = new ArrayList<Forum>();
		while (rs.next()) {
			Forum f = new Forum();
			f.setId(rs.getInt("fid"));
			f.setName(rs.getString("name"));
			f.setDescription(rs.getString("description"));
			f.setNrOfTopics(rs.getInt("nrOfTopics"));
			forums.add(f);
		}
		return forums.toArray(new Forum[0]);
	}

	public Topic[] getAllTopics(Connection conn, Forum forum)
			throws SQLException {
		PreparedStatement stat = conn
				.prepareStatement("select fid, t.tid, t.name, p.pid, p.message, p.postedOnDate from topics as t "
						+ "inner join "
						+ "(select tid, pid, message, postedOnDate, min(pid) from posts group by tid) as p "
						+ "on t.tid = p.tid " + "where fid = ?;");
		stat.setInt(1, forum.getId());
		ResultSet rs = stat.executeQuery();
		ArrayList<Topic> topics = new ArrayList<Topic>();
		while (rs.next()) {
			Topic t = new Topic();
			t.setId(rs.getInt("tid"));
			t.setName(rs.getString("name"));
			t.setForumId(forum.getId());

			Post post = new Post();
			post.setId(rs.getInt("pid"));
			post.setMessage(rs.getString("message"));
			post.setPostedOnDate(rs.getDate("postedOnDate"));

			t.setPost(post);
			topics.add(t);
		}
		return topics.toArray(new Topic[0]);
	}

	public User[] getAllUsers(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("select uid, name, isMod, isAdmin, password, email from users");
		ArrayList<User> users = new ArrayList<User>();
		while (rs.next()) {
			User u = new User();
			u.setEmail(rs.getString("email"));
			u.setPassword(rs.getString("password"));
			u.setId(rs.getInt("uid"));
			u.setUsername(rs.getString("name"));
			if (rs.getInt("isMod") == 1) {
				u.setModeratorRights(true);
			}
			if (rs.getInt("isMod") == 0) {
				u.setModeratorRights(false);
			}
			if (rs.getInt("isAdmin") == 1) {
				u.setAdminRights(true);
			}
			if (rs.getInt("isAdmin") == 0) {
				u.setAdminRights(false);
			}
			users.add(u);
		}
		return users.toArray(new User[0]);
	}

	public User removeUser(Connection conn, User user) throws SQLException {
		PreparedStatement stat = conn
				.prepareStatement("DELETE FROM users WHERE uid LIKE ?");
		stat.setInt(1, user.getId());
		stat.execute();
		return user;
	}

	public Post[] getAllPosts(Connection conn, Topic topic) throws SQLException {
		PreparedStatement stat = conn
				.prepareStatement("select pid, tid, posts.uid, postedOnDate, message, "
						+ "users.name, users.email "
						+ "from posts "
						+ "left join users on posts.uid = users.uid "
						+ "where tid = ?");
		stat.setInt(1, topic.getId());
		ResultSet rs = stat.executeQuery();

		ArrayList<Post> posts = new ArrayList<Post>();
		while (rs.next()) {
			Post p = new Post();
			p.setId(rs.getInt("pid"));
			p.setTopicId(rs.getInt("tid"));
			p.setUserId(rs.getInt("uid"));
			p.setPostedOnDate(rs.getDate("postedOnDate"));
			p.setMessage(rs.getString("message"));

			User user = new User();
			user.setId(rs.getInt("uid"));
			user.setUsername(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			p.setUser(user);

			posts.add(p);
		}
		return posts.toArray(new Post[0]);
	}

	public void createPost(Connection conn, Post post) throws SQLException {
		PreparedStatement prep = conn
				.prepareStatement("insert into posts (tid, uid, postedOnDate, message) "
						+ " values (?, ?, ?, ?);");
		prep.setInt(1, post.getTopicId());
		prep.setInt(2, post.getUserId());
		prep.setDate(3, post.getPostedOnDate());
		prep.setString(4, post.getMessage());

		prep.execute();
	}

	public User createUser(Connection conn, User user) throws SQLException {
		PreparedStatement prep = conn
				.prepareStatement("insert into users (name, isAdmin, isMod, password, email) "
						+ " values (?, ?, ?, ?, ?);");
		prep.setString(1, user.getUsername());

		if (user.getAdminRights())
			prep.setInt(2, 1);
		else
			prep.setInt(2, 0);
		if (user.getModeratorRights())
			prep.setInt(3, 1);
		else
			prep.setInt(3, 0);
		prep.setString(4, user.getPassword());
		prep.setString(5, user.getEmail());

		prep.execute();

		ResultSet rs = prep.getGeneratedKeys();
		rs.next();
		System.out.println("Du fick ID nr: " + rs.getInt(1));
		user.setId(rs.getInt(1));

		return user;
	}

	public User editUser(Connection conn, User user) throws SQLException {
		PreparedStatement prep = conn
				.prepareStatement("UPDATE users SET name = ?, isAdmin = ?, isMod = ? , password = ?, email = ? WHERE uid = ?");
		prep.setString(1, user.getUsername());

		if (user.getAdminRights())
			prep.setInt(2, 1);
		else
			prep.setInt(2, 0);
		if (user.getModeratorRights()) {
			prep.setInt(3, 1);
			System.out.println("ISMOD SATTES TILL TRUE; YO");
		} else
			prep.setInt(3, 0);
		System.out.println(user.getId());
		prep.setInt(4, user.getId());
		prep.setString(5, user.getPassword());
		prep.setString(6, user.getEmail());

		prep.execute();

		return user;
	}

	public Topic createTopic(Connection conn, Topic topic, Post post)
			throws SQLException {
		PreparedStatement prep = conn
				.prepareStatement("insert into topics (fid, name) values (?, ?)");
		prep.setInt(1, topic.getForumId());
		prep.setString(2, topic.getName());
		prep.execute();

		// fetch the topic id and use it for the associated post.
		ResultSet rs = prep.getGeneratedKeys();
		rs.next();
		int tid = rs.getInt(1);
		post.setTopicId(tid);
		createPost(conn, post);

		topic.setId(tid);
		topic.setPost(post);


		return topic;
	}

	public void deletePost(Connection conn, Post post) throws SQLException {
		PreparedStatement prep = conn.prepareStatement("DELETE FROM posts WHERE pid LIKE ?");
		System.out.println("databasemanager: trying to delete post with id: " + post.getId());
		prep.setInt(1, post.getId());


		prep.execute();

	}
	
	public void deleteTopic(Connection conn, Topic topic) throws SQLException {
		
		//Deleting posts belonging to topic no longer present
		PreparedStatement prep2 = conn.prepareStatement("DELETE FROM posts WHERE posts.tid = ?");
		prep2.setInt(1, topic.getId());
		prep2.execute();
		
		PreparedStatement prep = conn.prepareStatement("DELETE FROM topics WHERE tid LIKE ?");
		System.out.println("databasemanager: trying to delete topic with id: " + topic.getId());
		prep.setInt(1, topic.getId());
		



		prep.execute();

	}
}
