package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
	
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
			
			String query = "select * from user where username = '"+ username +"' and password = '"+ Utils.encryptMd5(password) +"';";
			
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()) {
	
				String id = rs.getString("id");

				setPassword_user(rs.getString("password"));
				
				user = new User(Integer.parseInt(id), rs.getString("username"),
						rs.getString("name"),
						rs.getString("nif"), 
						rs.getString("email"),
						rs.getString("address"),
						Utils.formatDateEU(rs.getDate("birthdate")),
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
	
	/*
	 * Nota: Los metodos de 'checkear' se quedaran como estaticos ya que es mas eficiente 
	 * debido a que no se va a instanciar a un objeto de esta clase para llamarlo.
	 */
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
		
		if(query.equalsIgnoreCase(username)) {
			return true;
		} else {
			return false;
		}
		
	} // cierre checkUser()
	
	/*
	 *  Metodo que inserta los datos en la base de datos. Devuelve true si han sido insertados correctamente
	 *  Sino devuelve false.
	 */
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
		
		// La siguiente condicion nos permitira saber si SQL ha insertado correctamente la fila de datos en la tabla user
		if (RowInsert > 0) {
		    return true;
		} else {
		    return false;
		}
		
	} // cierre signup()
	
	// Metodo adicional para comprobar que al registrar un nuevo NIF, no exista ya en la base de datos
	public static boolean checkNif(String nif) {
		String query = "";
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			query = "select nif from user where nif = '"+ nif +"';";
			
			ResultSet rs = stat.executeQuery(query);
				
			while(rs.next()) {
				query = rs.getString(1);
			}
			
			connect.close();
			stat.close();
			rs.close();
			
		} catch(Exception e) {
			System.out.println("Error en el metodo checkNif: " + e);
		}
		
		if(query.equalsIgnoreCase(nif)) {
			return true;
		} else {
			return false;
		}
		
	} // cierre checkNif()
	
	// Metodo adicional para comprobar que al registrar un nuevo EMAIL, no exista ya en la base de datos
	public static boolean checkEmail(String Email) {
		String query = "";
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			query = "select email from user where email = '"+ Email +"';";
			
			ResultSet rs = stat.executeQuery(query);
				
			while(rs.next()) {
				query = rs.getString(1);
			}
			
			connect.close();
			stat.close();
			rs.close();
			
		} catch(Exception e) {
			System.out.println("Error en el metodo checkEmail: " + e);
		}
		
		if(query.equalsIgnoreCase(Email)) {
			return true;
		} else {
			return false;
		}
		
	} // cierre checkEmail()

} // cierre class Database