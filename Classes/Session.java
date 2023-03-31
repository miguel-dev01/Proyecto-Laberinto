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
		// En 'user' se guardaran todos los datos del usuario que haya iniciado sesion: Objeto new user()
		user = data.login(username, password);
		String hash = Utils.encryptMd5(password);
		
		// Se introducira en un try-catch para controlar errores
		try {
			if(username.equalsIgnoreCase(user.getUsername()) && hash.equalsIgnoreCase(data.getPassword_user())) {
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
		System.out.println("ID del usuario: " + user.getId());
		System.out.println("Nombre de usuario: " + user.getUsername());
		System.out.println("Nombre y apellidos: " + user.getName());
		System.out.println("NIF: " + user.getNif());
		System.out.println("Email: " + user.getEmail());
		System.out.println("Direccion: " + user.getAddress());
		System.out.println("Fecha de nacimiento: " + user.getBirthdate() + " - " + Utils.getAge(user.getBirthdate()) + " años");
		System.out.println("Rol: " + user.getRole());
		
	} // Cierre showUser()
	
	public void Logout() {
		logged = false;
		System.out.println("\nSesion cerrada. Hasta pronto " + user.getName() + " ;)\n");
		user = new User();
		
	} // Cierre Logout()
	
	
	// Metodo que registra los datos en la base de datos
	public void signUp() {
		System.out.println(Config.CONSIDERATIONS_SIGNUP + "\n");
		
		// Requerimiento de nombre de usuario + Validacion de la nombre de usuario
		String username = Interface.getString("Introduce tu nombre de usuario: ");
		if(!Utils.validateUsername(username)) {
			System.out.println("\nNombre de usuario NO valido");
			return;
		}
		
		// El nombre de usuario no puede quedar vacio
		if(username.length() == 0) {
			System.err.println("\nEl nombre de usuario no puede quedar vacio\n");
			return;
		}
		
		// Si la siguiente condicion es true nos indica el siguiente error y finaliza (return)
		if(Database.checkUser(username)) {
			System.out.println("\nEl nombre de usuario ya existe. Debe usar otro.\n");
			return;
		}
		
		// Requerimiento de contrasenia + Validacion de la contrasenia
		String password = Interface.getString("Introduce una contraseña: ");
		if(!Utils.validatePassword(password)) {
			System.out.println("\nContraseña no valida");
			return;
		}
		String password_hash = Utils.encryptMd5(password);
		
		// Requerimiento de Nombre + Validacion del nombre (tambien vale para los apellidos)
		String name = Interface.getString("Introduce tu nombre: ");
		if(!Utils.validateName(name)) {
			System.out.println("\nNombre y/o apellidos no validos");
			return;
		}
		
		// Requerimiento de NIF + Validacion de NIF + Comprobacion de que NO exista en la base de datos
		// Nota: la comprobacion sobre la base de datos de que no exista un NIF igual se puede controlar
		// mediante un try catch, pero de cara al usuario se ha optado por hacerlo asi
		String nif = Interface.getString("Introduce NIF: ");
		if(!Utils.validateNif(nif)) {
			System.out.println("\nNIF introducido NO valido");
			return;
		}
		if(Database.checkNif(nif)) {
			System.out.println("\nEl NIF que has introducido ya existe en la base de datos.\nDebes hacer uso de otro.");
			return;
		}
		
		// Requerimiento de email + Validacion del email + Comprobacion de que NO exista en la base de datos
		String email = Interface.getString("Introduce email: ");
		if(!Utils.validateEmail(email)) {
			System.out.println("\nEMAIL introducido NO valido");
			return;
		}
		if(Database.checkEmail(email)) {
			System.out.println("\nEl email que has introducido ya existe en la base de datos.\nDebes usar otro.");
			return;
		}
		
		// Requerimiento de fecha de nacimiento + Validacion de la fecha de nacimiento
		String birthday = Interface.getString("Introduce fecha de nacimiento: ");
		if(!Utils.validateDate(birthday)) {
			System.out.println("\nFecha de nacimiento NO valida");
			return;
		}
		String dateTosql = Utils.formatDateSQL(birthday);
		
		// Requerimiento de direccion
		String address = Interface.getString("Introduce direccion: ");
		
		/*
		 * Una vez hayamos llegado hasta aqui, y solo si todos los datos han sido validados correctamente segun las
		 * comprobaciones, mediante un objeto de la clase Database, se enviaran todos los datos a la base de datos.
		 * Y se esperara la comprobacion de que dichos datos se hayan guardado de forma correcta
		 */
		Database data = new Database();
		boolean result_sign = data.signup(username, password_hash, name, nif, email, address, dateTosql);
		
		if(result_sign) {
			System.out.println("\nCuenta registrada correctamente en la base de datos.");
		} else {
			System.out.println("\nNo se ha podido insertar los datos correctamente en la base de datos. Intentelo de nuevo");
		}
		
	} // Cierre signUp()
	
} // cierre class Session