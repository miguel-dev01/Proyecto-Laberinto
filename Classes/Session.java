package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Session {
	
	public User user;
	private boolean logged;
	
	
	public Session() { // Constructor
		logged = false;
		user = new User();
		
	}
	
	// Metodo publico que hace visible la propiedad privada 'logged' para que esta pueda ser visible en main
	public boolean isLogged() {
		return logged;
	} 
	
	public void Login() {
		String username = Interface.getString("\nNombre de usuario: ");
		
		if(username.length() == 0) {
			System.err.println("\nEl nombre de usuario esta vacio\n");
			return;
		}
		
		String password = Interface.getString("\nContraseña: ");
		
		if(password.length() == 0) {
			System.err.println("\nEl campo de la contraseña no puede quedar vacio\n");
			return;
		}
		
		// Comprobar si el nombre de usuario y contraseña introducidos se halla en el fichero
		// Para ello llamamos al metodo que recorre el fichero e introducimos cada linea en un string separado
		// cuando encuentre almohadilla.
		// Y realizamos la comprobacion.
		ArrayList<String> line = readAllUser();
		for(int i = 0; i < line.size(); i++) {
			String[] currentUser = line.get(i).split("#");
			
			if(currentUser[0].equalsIgnoreCase(username) && currentUser[1].equals(password)) {
				logged = true;
				user.username = currentUser[0];
				user.name = currentUser[2];
				user.nif = currentUser[3];
				user.email = currentUser[4];
				user.address = currentUser[5];
				user.birthdate = currentUser[6];
				user.role = currentUser[7];
				
				break;
			}
			
		}
		
		if(logged) {
			System.out.println("\nSesion iniciada con exito! Bienvenid@ " + user.name + "!\n");
		} else {
			System.out.println("\nUsuario y/o password incorrectos. Intentelo de nuevo\n");
		}
		
	} // Cierre Login
	
	// Este metodo privado recorrera el fichero que contiene el nombre de usuario y contraseña
	private ArrayList<String> readAllUser() {
		
		ArrayList<String> allUsers = new ArrayList<String>();
		
		try {
			File myObj = new File(Config.FILE_PATH + Config.USERS_FILE);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				allUsers.add(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ha ocurrido un error.");
			e.printStackTrace();
		}
		
		return allUsers;
	} // Cierre ArrayList<String>
	
	public void showUser() {
		
		System.out.println("\nUSUARIO ACTUAL\n-------------\n");
		System.out.println("Nombre de usuario: " + user.username);
		System.out.println("Nombre y apellidos: " + user.name);
		System.out.println("NIF: " + user.nif);
		System.out.println("Email: " + user.email);
		System.out.println("Direccion: " + user.address);
		System.out.println("Fecha de nacimiento: " + user.birthdate);
		System.out.println("Rol: " + user.role);
		
		
	} // Cierre showUser()
	
	public void Logout() {
		logged = false;
		System.out.println("\nSesion cerrada. Hasta pronto " + user.name + " ;)\n");
		user = new User();
		
	} // Cierre Logout()
	
	
	// Metodo que registra
	public void signUp() {
		
		String username = Interface.getString("Introduce tu nombre de usuario: ");
		
		// El nombre de usuario no puede quedar vacio
		if(username.length() == 0) {
			System.err.println("\nEl nombre de usuario no puede quedar vacio\n");
			return;
		}
		
		// Si la siguiente condicion es true nos indica el siguiente error y finaliza (return)
		if(checkUser(username)) {
			System.out.println("\nEl nombre de usuario ya existe\n");
			return;
		}
		
		// Pedimos los datos
		String password = Interface.getString("Introduce una contraseña: ");
		String name = Interface.getString("Introduce tu nombre: ");
		String nif = Interface.getString("Introduce NIF: ");
		String email = Interface.getString("Introduce email: ");
		String address = Interface.getString("Introduce direccion: ");
		String birthday = Interface.getString("Introduce fecha de nacimiento: ");
		
		// Concatenamos los datos recibidos por teclado para que pueda ser escrito en el fichero
		String newLine = "\n" + username + "#" + password + "#" + name + "#" + nif + "#" + email + "#" + address + "#" + birthday + "#user";
		
		try {
			FileWriter myWriter = new FileWriter(Config.FILE_PATH + Config.USERS_FILE, true);
			myWriter.write(newLine);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error");
			e.printStackTrace();
		}
		
		
	} // Cierre signUp()
	
	private boolean checkUser(String username) {
		
		boolean exists = false;
		
		// Llamamos al array que recorre todo el fichero
		ArrayList<String> line = readAllUser();
		
		for(int i = 0; i < line.size(); i++) {
			String[] currentUser = line.get(i).split("#");
			
			if(currentUser[0].equalsIgnoreCase(username)) {
				exists = true;
				break;
			}
			
		}
		
		return exists;
	} // Cierre checkUser()
	
}