package Classes;

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
		
		Database data = new Database();
		user = data.login(username, password);
		
		// Se introducira en un try-catch para controlar errores
		try {
			if(username.equalsIgnoreCase(user.getUsername()) && password.equalsIgnoreCase(data.getPassword_user())) {
				logged = true;
			} else {
				logged = false;
			}
		} catch(Exception NullPointerException) {}
			
		// Mensaje de exito o no exito al iniciar sesion
		if(logged) {
			System.out.println("\nSesion iniciada con exito! Bienvenid@ " + user.getName() + "!\n");
		} else {
			System.out.println("\nUsuario y/o password incorrectos. Intentelo de nuevo\n");
		}
		
	} // Cierre Login()
	
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
		if(Database.checkUser(username)) {
			System.out.println("\nEl nombre de usuario ya existe. Use otro\n");
			return;
		}
		
		// Pedimos los datos
		String password = Interface.getString("Introduce una contraseña: ");
		String name = Interface.getString("Introduce tu nombre: ");
		String nif = Interface.getString("Introduce NIF: ");
		String email = Interface.getString("Introduce email: ");
		String address = Interface.getString("Introduce direccion: ");
		String birthday = Interface.getString("Introduce fecha de nacimiento: ");
		
		Database data = new Database();
		boolean result = data.signup(username, password, name, nif, email, address, birthday);
		
		if(result) {
			System.out.println("\nCuenta registrada correctamente en la base de datos.");
		} else {
			System.out.println("\nNo se ha podido insertar los datos correctamente en la base de datos. Intentelo de nuevo");
		}
		
	} // Cierre signUp()
	
} // cierre class Session