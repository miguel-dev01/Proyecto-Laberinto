package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	
	private static String IP = "localhost";
	private static String PORT = "3306";
	private static String DB = "maze";
	private static String USER = "root";
	private static String PASS = "simiR12";
	private static String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DB;

	private String passwordUser;

	public String getpasswordUser() {
		return this.passwordUser;
	}
	
	public void setpasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	
	/**
	 * Metodo que permite al usuario iniciar sesion en la base de datos
	 * @param username
	 * @param password
	 * @return Variable de tipo user con los datos del usuario que se ha logueado
	 */
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

				setpasswordUser(rs.getString("password"));
				
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
	
	/**
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
		
		return query.equalsIgnoreCase(username);
		
	} // cierre checkUser()
	
	/**
	 *  Metodo que inserta los datos en la base de datos. Devuelve true si han sido insertados 
	 *  correctamente. Sino devuelve false.
	 */
	public boolean signup(String username, String password, String name, String nif, String email, String address, String birthday) {
		int rowInsert = 0;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			String insertion = "insert into user (username, password, name, nif, email, address, birthdate) values ('" 
		             + username + "', '" + password + "', '" + name + "', '" + nif + "', '" + email + "', '" 
		             + address + "', '" + birthday + "')";

			rowInsert = stat.executeUpdate(insertion);
			
			connect.close();
			stat.close();
			
		} catch(Exception e) {
			System.out.println("Error en el metodo signup: " + e);
		}
		
		// La siguiente condicion nos permitira saber si SQL ha insertado correctamente la fila de datos en la tabla user
		return rowInsert > 0;
		
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
		
		return query.equalsIgnoreCase(nif);
		
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
		
		return query.equalsIgnoreCase(Email);
		
	} // cierre checkEmail()
	
	/**
	 * Metodo que hace el cambio en la base de datos sobre el dato que ha recibido desde un metodo de la
	 * clase ModifyData.java
	 * @return Valor booleano que indica si el dato se ha actualizado correctamente en la base de datos
	 */
	public boolean insertOnlyData(String data, int option, String username) {		
		int rowInsert = 0;
		String column = "";
		String update = "";
		
		/**
		 * Segun el dato que se escoja en el que se quiera hacer el cambio en la base de datos, 
		 * debera cambiar el nombre de la columna en el insert de SQL para poder realizar la insercion
		 * correctamente en la columna deseada, dependiendo del dato en que escoja el usuario, sera una
		 * columna u otra gracias a la estructura de control switch()
		 * El nombre de la variable 'column' contiene el nombre exacto de la columna de la tabla user en su caso
		 */
		switch (option) {
			case 1:
				column = "name";
				break;
			case 2:
				column = "password";
				data = Utils.encryptMd5(data);
				break;
			case 3:
				column = "nif";
				break;
			case 4:
				column = "email";
				break;
			case 5:
				column = "address";
				break;
			case 6:
				column = "birthdate";
				data = Utils.formatDateSQL(data);
				break;
			default:
				System.err.println("\nAlgo ha ido mal.");
		}
		
		// Actualizar el dato en la base de datos
		update = "update user set <<column>> = '<<data>>' where username = '<<username>>';";
		update = update.replaceAll("<<username>>", username);
		update = update.replaceAll("<<column>>", column);
		update = update.replaceAll("<<data>>", data);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			rowInsert = stat.executeUpdate(update);
			connect.close();
			stat.close();
		} catch(Exception e) {
			System.out.println("Error en el metodo insertOnlyData: " + e);
		}
		
		/**La siguiente condicion nos permitira saber si MySQL ha actualizado correctamente el
		 * registro en la tabla user, devolvera false si se ha mantenido en 0 y devolvera true
		 * si ha sumado mas de 0.
		 */
		return rowInsert > 0;
		
	} // cierre insertOnlyData()
	
	public static boolean deleteUserDB(String username, String pass) {
		
		int rowDelete = 0;
		String deleteTupla = "delete from user where username = '<<username>>' and password = '<<pass>>';";
		deleteTupla = deleteTupla.replaceAll("<<username>>", username);
		deleteTupla = deleteTupla.replaceAll("<<pass>>", pass);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			rowDelete = stat.executeUpdate(deleteTupla);
			connect.close();
			stat.close();
		} catch(Exception e) {
			System.out.println("Error en el metodo deleteUserDB: " + e);
		}
		
		return rowDelete > 0;
	} // cierre deleteUserDB()
	
	/**
	 * Metodo similar al anterior pero pidiendo un solo parametro para poder borrar el usuario
	 * @param username
	 * @return Devuelve la confirmacion si la el registro(fila) se ha borrado correctamente en la base
	 * de datos.
	 */
	public static boolean deleteUserDB(String username) {
		
		int rowDelete = 0;
		String deleteTupla = "delete from user where username = '<<username>>';";
		deleteTupla = deleteTupla.replaceAll("<<username>>", username);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			rowDelete = stat.executeUpdate(deleteTupla);
			connect.close();
			stat.close();
		} catch(Exception e) {
			System.out.println("Error en el metodo deleteUserDB: " + e);
		}
		
		return rowDelete > 0;
	} // cierre deleteUserDB()
	
	/**
	 * Metodo String que accede a la base de datos para saber si el rol de un usuario es 'user' o 'admin'
	 */
	public String showRol(String username) {
		String query = "";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			query = "select role from user where username = '"+ username +"';";
			
			ResultSet rs = stat.executeQuery(query);
				
			while(rs.next()) {
				query = rs.getString(1);
			}
			
			connect.close();
			stat.close();
			rs.close();
		} catch(Exception e) {
			System.out.println("Error en el metodo showRol: " + e);
		}
		
		return query;
	
	} // cierre showRol()
	
	/**
	 * Metodo pensado para la clase Admin, que provee al usuario administrador los usuarios que 
	 * hay en base de datos
	 * @return ArrayList con los nombres y el ID de los usuarios de la BD
	 */
	public static ArrayList<String> listUsers() {
		ArrayList<String> listUser = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			String query = "select id, username from user;"; // Consulta SQL
			ResultSet rs = stat.executeQuery(query);
				
			while(rs.next()) {
				listUser.add("\nID: " + rs.getString("id"));
				listUser.add("\nNombre de usuario: " + rs.getString("username")+"\n");
			}
			
			connect.close();
			stat.close();
			rs.close();
		} catch(Exception e) {
			System.out.println("Error en el metodo listUsers: " + e);
		}
		
		return listUser;
		
	} // cierre listUsers()
	
	/**
	 * Metodo estatico que devuelve un objeto de tipo User con todos los datos provenientes de la 
	 * base de datos. Dicho usuario se selecciona mediante una consulta SQL en la que se le envia
	 * por parametro del metodo el ID del usuario al que seleccionar.
	 * @param id
	 * @return Objeto de la clase User con datos de usuario
	 */
	public static User selectUser(String id) {
		User user = null;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USER, PASS);
			Statement stat = connect.createStatement();
			
			String query = "select * from user where id = '"+ id +"';";
			
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()) {
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
			System.err.println("Error en el metodo selectInfoUser: " + e);
		}
		if(user != null) {
			return user;
		} else {
			return null;
		}
	
	} // cierre selectUser()
	
	
} // cierre class Database