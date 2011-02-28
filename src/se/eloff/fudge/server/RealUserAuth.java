package se.eloff.fudge.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import se.eloff.fudge.client.bean.User;
import se.eloff.fudge.server.LoginServiceImpl.UserAuthInterface;

public class RealUserAuth implements UserAuthInterface {

	public User getLoggedInUser() {
		testSQL();
		return new User("gwt_test", "123");
	}

	public boolean validateUser(String username, String password) {
		return true;
	}
	
	public String testSQL() {
	      String str = "Result: ";
	      try {
	         Class.forName("org.sqlite.JDBC");        // SQLite
	         Connection conn = DriverManager.getConnection(
					"jdbc:sqlite:/" + System.getProperty("user.home") +
					"/test.db");        // SQLite DB

	         // If you want to try MySQL instead, you can use the two lines below, 
	         // together with the jar file /home/TDDD24/lib/mysql-connector-java-5.1.11-bin.jar
	         // Class.forName("com.mysql.jdbc.Driver");
		 // Connection conn = DriverManager.getConnection("jdbc:mysql://www-und.ida.liu.se?" +
		 //                      "user=user&password=pass");

	         Statement stat = conn.createStatement();
	         stat.executeUpdate("drop table if exists people;");

	         stat.executeUpdate("create table people (name varchar(20), occupation varchar(20));");

	         PreparedStatement prep = conn
	               .prepareStatement("insert into people values (?, ?);");
	         prep.setString(1, "Gandhi");
	         prep.setString(2, "politics");
	         prep.execute();

	         ResultSet rs = stat.executeQuery("select * from people;");
	         while (rs.next()) {
	            str += "name = " + rs.getString("name");
	            str += "\njob = " + rs.getString("occupation");
	         }
	         rs.close();
	         conn.close();
	      } catch (Exception e) {
	         str += e.toString();
	         e.printStackTrace();
	      } 
	      return str;
	 }


}
