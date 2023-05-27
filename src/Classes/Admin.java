package Classes;

import java.util.ArrayList;

public class Admin {

	private static Session session = new Session();
	private static ArrayList<String> listUsers = Database.listUsers();
	// Variable de tipo user que almacenara todos los datos proveniente de la BD.
	// Dichos datos seran elegidos por el administrador
	private static User user;
	public static User getUser() {
		return user;
	}
	public static void setUser(User user) {
		Admin.user = user;
	}

	private static ModifyData modifyData = new ModifyData();
	
	public Admin() {
		user = new User();
	}

	/**
	 * Metodo que muestra al usuario admin las opciones que tiene para hacer como administrador
	 */
	public static void showOptions() {
		
		System.out.println(Config.MENU_USERADMIN);
		int option = Interface.getInt("\n¿Que accion quieres realizar?: ");
		String id;
		
		switch(option) {
			case 1:
				session.signUp();
				break;
			case 2:
				System.out.println("\nSelecciona un usuario: ");
				showUsersForAdmin();
				
				id = Interface.getString("\nIntroduce ID del usuario para seleccionarlo: ");
				user = Database.selectUser(id);
				if(user != null) {
					showUser();
				} else {
					System.err.println("\nEl ID seleccionado no tiene asignado ningun usuario en la base de datos.");
				}
				break;
			case 3:
				System.out.println("\nSelecciona un usuario: ");
				showUsersForAdmin();
				
				id = Interface.getString("\nIntroduce ID del usuario para seleccionarlo: ");
				user = Database.selectUser(id);
				if(user != null) {
					modifyData.setUsername(user.getUsername());
					modifyData.requestDataForAdmin();
				} else {
					System.err.println("\nEl ID seleccionado no tiene asignado ningun usuario en la base de datos.");
				}
				break;
			case 4:
				System.out.println("\nSelecciona un usuario: ");
				showUsersForAdmin();
				
				id = Interface.getString("\nIntroduce ID del usuario para seleccionarlo: ");
				user = Database.selectUser(id);
				if(user != null) {
					boolean deleteRow = Database.deleteUserDB(user.getUsername());
					System.out.println(deleteRow ? "\nSe ha borrado el usuario correctamente":"\nNo se ha podido eliminar el usuario");
					user = new User();
					listUsers = Database.listUsers();
				} else {
					System.err.println("\nEl ID seleccionado no tiene asignado ningun usuario en la base de datos.");
				}
				break;
			default:
				System.out.println("\nNumero invalido");
		} // cierre switch
				
		
	} // cierre showOptions()
	
	public static void showUsersForAdmin() {
		
		for(int i = 0; i < listUsers.size(); i++) {
			//System.out.println("ID " +  " - " + " Nombre de usuario");
			System.out.print(listUsers.get(i));
		}
		
	} // cierre showUsersForAdmin()
	
	/**
	 * Metodo que muestra la informacion del usuario elegido por el Administrador
	 */
	public static void showUser() {
		System.out.println("\nUSUARIO SELECCIONADO:\n-------------\n");
		System.out.println("ID del usuario: " + user.getId());
		System.out.println("Nombre de usuario: " + user.getUsername());
		System.out.println("Nombre y apellidos: " + user.getName());
		System.out.println("NIF: " + user.getNif());
		System.out.println("Email: " + user.getEmail());
		System.out.println("Direccion: " + user.getAddress());
		System.out.println("Fecha de nacimiento: " + user.getBirthdate() + " - " + Utils.getAge(user.getBirthdate()) + " años");
		System.out.println("Rol: " + user.getRole());
		
	} // Cierre showUser()
	
} // cierre class
