package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverManagerConnectionPool  {
	
	private static ArrayList<Connection> rilasciaConnessioni;
	
	static {
	    rilasciaConnessioni = new ArrayList<Connection>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("Driver caricato con successo!");
	    } catch (ClassNotFoundException e) {
	        System.out.println("Driver non trovato: " + e.getMessage());
	    }

	}

	
	private static synchronized Connection creaConnessioneDB() throws SQLException {
		Connection newConnection = null;
		String ip = "localhost";
		String port = "3306";
		String db = "UniMeet";
		String username = "root";
		String password = "ciro1996";

		newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ port+"/"+db+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
		newConnection.setAutoCommit(false);
		return newConnection;
	}

	public static synchronized Connection getConnessione() throws SQLException {
		Connection connection;

		if (!rilasciaConnessioni.isEmpty()) {
			connection = (Connection) rilasciaConnessioni.get(0);
			rilasciaConnessioni.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnessione();
			} catch (SQLException e) {
				connection.close();
				connection = getConnessione();
			}
		} else {
			connection = creaConnessioneDB();		
		}

		return connection;
	}

	public static synchronized void rilasciaConnessione(Connection connection) throws SQLException {
		if(connection != null) rilasciaConnessioni.add(connection);
	}
}
