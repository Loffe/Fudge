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
import se.eloff.fudge.client.bean.Topic;

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
			String filePath = "/" + System.getProperty("user.home")
					+ "/workspace/Fudge/war/" + filename;

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
	}

	private void createTableUsers(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists users;");

		stat.executeUpdate("create table users (uid integer primary key, "
				+ "name varchar(20), password varchar(20));");

		// Create default user
		PreparedStatement prep = conn
				.prepareStatement("insert into users values (?, ?, ?);");
		prep.setInt(1, 1);
		prep.setString(2, "gwt");
		prep.setString(3, "password");
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

		// Create default user
		PreparedStatement prep = conn
				.prepareStatement("insert into topics (name, fid) "
						+ " values (?, ?);");
		for (int i = 1; i <= 10; i++) {
			prep.setString(1, "Topic number " + i);
			prep.setInt(2, 2 + i % 4);
			prep.execute();
		}
	}

	/**
	 * Check if a user with the given user name and password exists.
	 * 
	 * @param conn
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean checkUserCredentials(Connection conn, String username,
			String password) throws SQLException {
		PreparedStatement stat = conn
				.prepareStatement("select * from users where name = ? and password = ?");
		stat.setString(1, username);
		stat.setString(2, password);
		ResultSet rs = stat.executeQuery();
		int numRows = 0;
		while (rs.next()) {
			++numRows;
		}
		rs.close();
		return numRows == 1;
	}

	public Forum[] getAllForums(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("select fid, name, description from forums");
		ArrayList<Forum> forums = new ArrayList<Forum>();
		while (rs.next()) {
			Forum f = new Forum();
			f.setId(rs.getInt("fid"));
			f.setName(rs.getString("name"));
			f.setDescription(rs.getString("description"));
			forums.add(f);
		}
		return forums.toArray(new Forum[0]);
	}

	public Topic[] getAllTopics(Connection conn, Forum forum)
			throws SQLException {
		PreparedStatement stat = conn
				.prepareStatement("select tid, name from topics where fid = ?");
		stat.setInt(1, forum.getId());
		ResultSet rs = stat.executeQuery();
		ArrayList<Topic> topics = new ArrayList<Topic>();
		while (rs.next()) {
			Topic t = new Topic();
			t.setId(rs.getInt("tid"));
			t.setName(rs.getString("name"));
			t.setForumId(forum.getId());
			topics.add(t);
		}
		return topics.toArray(new Topic[0]);
	}
}
