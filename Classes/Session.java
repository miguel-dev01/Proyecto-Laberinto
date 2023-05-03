package Classes;

public class Session {
	
	public User user;
	private boolean logged;
	private Database data;
	
	public Session() { // Constructor
		logged = false;
		user = new User();
		data = new Database();
	}
	
	// Metodo publico que hace visible la propiedad privada 'logged' para que esta pueda ser visible en main
	public boolean isLogged() {
		return logged;
	} 
	
	public void login() {
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
		
		// En 'user' se guardaran todos los datos del usuario que haya iniciado sesion: 'Objeto new user()'
		user = data.login(username, password);
		
		// Se introducira en un try-catch para controlar errores
		try {
			if(username.equalsIgnoreCase(user.getUsername())) {
				logged = true;
			} else {
				logged = false;
			}
		} catch(NullPointerException e) {}
			
		// Mensaje de exito o no exito al iniciar sesion
		if(logged) {
			System.out.println("\nSesion iniciada con exito! Bienvenid@ " + user.getName() + "!\n");
			Log.loggerEvents("Login exitoso", "Usuario: " + user.getUsername());
		} else {
			System.out.println("\nUsuario y/o password incorrectos. Intentelo de nuevo\n");
			Log.loggerEvents("Login fallido", "Usuario: " + username);
		}
		
	} // Cierre login()
	
	public void showUser() {
		Log.loggerEvents("Usuario actual", "Usuario: " + user.getUsername() + ", ID: " + user.getId());//Evento
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
	
	public void logout() {
		Log.loggerEvents("Sesion cerrada", "Usuario: " + user.getUsername());//Evento
		System.out.println("\nSesion cerrada. Hasta pronto " + user.getName() + " ;)\n");
		logged = false;
		user = new User();
		data = new Database();
		
	} // Cierre Logout()
	
	
	// Metodo que registra los datos en la base de datos
	public void signUp() {
		// TODO NO DUPLICAR EL CODIGO DE LOS EVENTOS!!!!
		
		System.out.println(Config.CONSIDERATIONS_SIGNUP + "\n");
		
		// Requerimiento de nombre de usuario + Validacion de la nombre de usuario
		String username = Interface.getString("Introduce tu nombre de usuario: ");
		if(!Utils.validateUsername(username)) {
			System.out.println("\nNombre de usuario NO valido");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		
		// El nombre de usuario no puede quedar vacio
		if(username.length() == 0) {
			System.err.println("\nEl nombre de usuario no puede quedar vacio\n");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		
		// Si la siguiente condicion es true nos indica el siguiente error y finaliza (return)
		if(Database.checkUser(username)) {
			System.out.println("\nEl nombre de usuario ya existe. Debe usar otro.\n");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		
		// Requerimiento de contrasenia + Validacion de la contrasenia
		String password = Interface.getString("Introduce una contraseña: ");
		if(!Utils.validatePassword(password)) {
			System.out.println("\nContraseña no valida");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		String passwordHash = Utils.encryptMd5(password);
		
		// Requerimiento de Nombre + Validacion del nombre (tambien vale para los apellidos)
		String name = Interface.getString("Introduce tu nombre: ");
		if(!Utils.validateName(name)) {
			System.out.println("\nNombre y/o apellidos no validos");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		
		// Requerimiento de NIF + Validacion de NIF + Comprobacion de que NO exista en la base de datos
		// Nota: la comprobacion sobre la base de datos de que no exista un NIF igual se puede controlar
		// mediante un try catch, pero de cara al usuario se ha optado por hacerlo asi
		String nif = Interface.getString("Introduce NIF: ");
		if(!Utils.validateNif(nif)) {
			System.out.println("\nNIF introducido NO valido");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		if(Database.checkNif(nif)) {
			System.out.println("\nEl NIF que has introducido ya existe en la base de datos.\nDebes hacer uso de otro.");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		
		// Requerimiento de email + Validacion del email + Comprobacion de que NO exista en la base de datos
		String email = Interface.getString("Introduce email: ");
		if(!Utils.validateEmail(email)) {
			System.out.println("\nEMAIL introducido NO valido");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		if(Database.checkEmail(email)) {
			System.out.println("\nEl email que has introducido ya existe en la base de datos.\nDebes usar otro.");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);
			return;
		}
		
		// Requerimiento de fecha de nacimiento + Validacion de la fecha de nacimiento
		String birthday = Interface.getString("Introduce fecha de nacimiento: ");
		if(!Utils.validateDate(birthday)) {
			System.out.println("\nFecha de nacimiento NO valida");
			return;
		}
		// Cuando se haya validado la fecha correctamente, se pasara a formato del tipo dato de SQL: Date
		String dateTosql = Utils.formatDateSQL(birthday);
		
		// Requerimiento de direccion
		String address = Interface.getString("Introduce direccion: ");
		
		/*
		 * Una vez hayamos llegado hasta aqui, y solo si todos los datos han sido validados correctamente segun las
		 * comprobaciones, mediante un objeto de la clase Database, se enviaran todos los datos a la base de datos.
		 * Y se esperara la comprobacion de que dichos datos se hayan guardado de forma correcta
		 */
		
		boolean resultSign = data.signup(username, passwordHash, name, nif, email, address, dateTosql);
		
		if(resultSign) {
			System.out.println("\nCuenta registrada correctamente en la base de datos.");
			Log.loggerEvents("Registro exitoso", "Usuario: " + username);//Evento
		} else {
			System.out.println("\nNo se han podido insertar los datos correctamente en la base de datos. Intentelo de nuevo");
			Log.loggerEvents("Registro fallido", "Usuario: " + username);//Evento
		}
		
	} // Cierre signUp()
	
} // cierre class Session