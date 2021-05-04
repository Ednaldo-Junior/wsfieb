package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DSconexao {
	private String localhost;
	private int porta;
	private String database;
	private String username;
	private String password;

	private Connection conection;

	public DSconexao() {
		try {
			// localhost = "127.0.0.1";
			// porta = 3050;
			// database =
			// "D:\\SISTEMAS\\QUANTUM\\Quantum2005\\Dados\\QUANTUM.fdb?encoding=ISO8859_1";
			// username = "sysdba";
			// password = "masterkey";
			// String url =
			// "jdbc.firebirdsql:"+localhost+"/"+porta+":"+database;
			//
			// Class.forName("org.firebirdsql.jdbc.FBDriver");
			//
			// // DriverManager.registerDriver( new Driver());
			// conection = DriverManager.getConnection(url,username,password);

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			conection = DriverManager.getConnection(
					"jdbc:firebirdsql:127.0.0.1/3050:D:\\SISTEMAS\\QUANTUM\\Quantum2005\\Dados\\QUANTUM.fdb?encoding=ISO8859_1",
					"sysdba", "masterkey");

			System.out.println("Conectado");
		} catch (SQLException e) {
			System.err.println("Erro da conexão: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro da geral: " + e.getMessage());
		}
	}

	public Connection getConnection() {
		return conection;
	}
}
