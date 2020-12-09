package teste_firebird;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		Connection con = connect();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ejsg001");
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Connection connect() {
		
		Connection con = null;
		
		try{
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = 
				DriverManager.getConnection(
				"jdbc:firebirdsql:127.0.0.1/3050:C:\\media\\dados\\qsgo.fdb?encoding=ISO8859_1",
				"sysdba",
				"masterkey");
			return con;
		}catch (Exception e){
			System.out.println("Não foi possível conectar so banco: "+e.getMessage());
			return null;
		}
	}
}
