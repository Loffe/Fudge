package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:/"
					+ System.getProperty("user.home") + "/test.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void createTables(Connection conn) throws SQLException {
		createTableUsers(conn);

		createTableForums(conn);
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
						+ " values (?, ?, ?);");
		for (int i = 0; i < 10; i++) {
			prep.setString(1, "Forum number " + i);
			prep.setString(2, "a very very interesting and happy place");
			prep.execute();
		}
	}
	
	/**
	 * Check if a user with the given user name and password exists.
	 * @param conn
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean checkUserCredentials(Connection conn, String username, String password) throws SQLException {
		PreparedStatement stat = conn.prepareStatement("select * from users where name = ? and password = ?");
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
}
