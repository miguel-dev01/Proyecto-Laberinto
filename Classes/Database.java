package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database extends User {
	
	private static String IP = "localhost";
	private static String PORT = "3306";
	private static String DB = "maze";
	private static String USER = "root";
	private static String PASS = "simiR12";
	private static String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DB;
	
	private String password_user;
	
	public Database() {}


	public String getPassword_user() {
		return password_user;
	}
	
	public void setPassword_user(String password_user) {
		this.password_user = password_user;
	}
	
	public User login(String username, String password) {
		
		User user = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			String query = "select * from user where username = '"+ username +"' and password = '"+ password +"';";
			
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()) {
	
				String id = rs.getString("id");

				setPassword_user(rs.getString("password"));
				
				user = new User(Integer.parseInt(id), rs.getString("username"),
						rs.getString("name"),
						rs.getString("nif"), 
						rs.getString("email"),
						rs.getString("address"),
						rs.getString("birthdate"),
						rs.getString("role"));
				
			}
			
			stat.close();
			rs.close();
			connect.close();
		} catch(Exception e) {
			System.err.println("Error: " + e);
		}
		
		if(user != null) {
			return user;
		} else {
			return null;
		}
	
	} // cierre login()
	
	
	public static boolean checkUser(String username) {
		String query = "";
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			query = "select username from user where username = '"+ username +"';";
			
			ResultSet rs = stat.executeQuery(query);
				
			while(rs.next()) {
				query = rs.getString(1);
			}
			
			connect.close();
			stat.close();
			rs.close();
			
		} catch(Exception e) {
			System.out.println("Error en el metodo chekUser: " + e);
		}
		
		if(query.equals(username)) {
			return true;
		} else {
			return false;
		}
		
	} // cierre checkUser()
	
	
	public boolean signup(String username, String password, String name, String nif, String email, String address, String birthday) {
		int RowInsert = 0;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			String insertion = "insert into user (username, password, name, nif, email, address, birthdate) values ('" 
		             + username + "', '" + password + "', '" + name + "', '" + nif + "', '" + email + "', '" 
		             + address + "', '" + birthday + "')";

			RowInsert = stat.executeUpdate(insertion);
			
			connect.close();
			stat.close();
			
		} catch(Exception e) {
			System.out.println("Error en el metodo signup: " + e);
		}
		
		// La siguiente condicion nos permitira saber si sql ha insertado correctamente la fila de datos en la tabla user
		if (RowInsert > 0) {
		    return true;
		} else {
		    return false;
		}
		
	} // cierre signup()
	
	
	
	// METODO DE PRUEBAS
	public static void main(String[] args) {
		
		String username = Interface.getString("\nNombre de usuario: ");
		String password = Interface.getString("\nContrasenia: ");
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			String query1 = "select * from user where username = '"+ username +"' and password = '"+ password +"';";
			
			ResultSet rs = stat.executeQuery(query1);
			
			while(rs.next()) {
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("username"));
				System.out.println(rs.getString("role"));
				
			}
			
			
			stat.close();
			rs.close();
			connect.close();
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		
		
		
		
	}
	
	
	
	
	
} // cierre class Database