package Classes;
import Main.Main;

/**
 * Clase con metodos que permiten al usuario cambiar sus datos y acometerlos en la base de datos
 */

public class ModifyData {
    
	private Database database = new Database();
	private String username;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Este metodo permite que el usuario pueda cambiar sus datos
	 * Recogera los resultados verificados y validados y los enviara a la clase Database.java
	 * para que dicha clase se los envie a la base de datos.
	 */
	public void requestData() {
		/**
		 *  Variable booleana que guarda el resultado de lo que se ha hecho en base de datos.
		 *  True si todo ha ido bien. O false si algo ha ido mal.
		 */
		boolean resultBD = false;
		
		// Consideraciones y politicas de seguridad y validacion para registrar el dato correctamente
		System.out.println(Config.CONSIDERATIONS_SIGNUP2);
		
		// Aqui van los mensajes al usuario para elegir el campo que quiere modificar
		System.out.println(Config.MENU_UPDATE_DATA_USER);

		int option = Interface.getInt("\n¿Que dato quieres cambiar?: ");
		
		switch (option) {
			case 1:
				// Pedimos nombre
				String name = Interface.getString("\nIntroduce nombre: ");
				// Validamos el dato
				if(!Utils.validateName(name)) {
					System.out.println("\nNombre y/o apellidos no validos");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(name, 1, this.getUsername());
				/** Una vez se ha insertado el dato en BD. El dato nuevo que hayamos introducido
				 * se guardara en el objeto meeting de Session, que tiene a su vez el objeto user
				 * con los datos del usuario en la sesion actual. Al pasarselo en un metodo set,
				 * cuando el usuario realice la accion de mostrar todos los datos del usuario,
				 * todo esta actualizado correctamente y no tendra que cerrar la sesion para ver
				 * el dato que recientemente a actualizado. Y haremos lo mismo para los demas campos.
				 * Se llama al objeto que se encuentra en Main ya que si llamamos simplemente a un nuevo
				 * objeto de Session todos los datos nos apareceran como nulos.
				 */ 
				Main.meeting.user.setName(name);
				break;
			case 2:
				// Pedimos contrasenia
				String pass = Interface.getString("\nIntroduce nueva contraseña: ");
				// Validamos el dato
				if(!Utils.validatePassword(pass)) {
					System.out.println("\nContraseña NO valida");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(pass, 2, this.getUsername());
				break;
			case 3:
				// Pedimos nif
				String nif = Interface.getString("\nIntroduce NIF: ");
				// Validamos el dato
				if(!Utils.validateNif(nif)) {
					System.out.println("\nNombre y/o apellidos no validos");
					return;
				}
				if(Database.checkNif(nif)) {
					System.out.println("\nEl NIF que has introducido ya existe en la base de datos.\nDebes hacer uso de otro.");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(nif, 3, this.getUsername());
				Main.meeting.user.setNif(nif);
				break;
			case 4:
				// Pedimos email
				String email = Interface.getString("\nIntroduce email: ");
				// Validamos el dato
				if(!Utils.validateEmail(email)) {
					System.out.println("\nEMAIL introducido NO valido");
					return;
				}
				if(Database.checkEmail(email)) {
					System.out.println("\nEl email que has introducido ya existe en la base de datos.\nDebes usar otro.");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(email, 4, this.getUsername());
				Main.meeting.user.setEmail(email);
				break;
			case 5:
				// Pedimos direccion
				String address = Interface.getString("\nIntroduce la direccion: ");
				resultBD = database.insertOnlyData(address, 5, this.getUsername());
				Main.meeting.user.setAddress(address);
				break;
			case 6:
				// Pedimos fecha de nacimiento
				String birthdate = Interface.getString("\nIntroduce fecha de nacimiento: ");
				// Validamos el dato
				if(!Utils.validateDate(birthdate)) {
					System.out.println("\nFecha de nacimiento NO valida");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(birthdate, 6, this.getUsername());
				Main.meeting.user.setBirthdate(birthdate);
				break;
			case 7:
				// Pedimos contrasenia del usuario para confirmar y borrar el usuario
				String password = Interface.getString("\nConfirma la contraseña para borrar tu usuario: ");
				
				resultBD = Database.deleteUserDB(this.getUsername(), Utils.encryptMd5(password));
				if(resultBD) {
					System.out.println("\nUsuario eliminado exitosamente.");
					// Llamada al metodo en Main que cierra la sesion tras eliminar el usuario
					Main.meeting.logout2();
					return;
				} else {
					System.err.println("\nNo se ha podido eliminar el usuario.");
					return;
				}
			default:
				System.out.println("\nNumero no valido.");
		} // cierre switch
		
		/**
		 * Si hemos llegado hasta aqui, es que todo ha ido bien y la insercion del dato en base de datos
		 * que haya dado como entrada el usuario es que se ha insertado correctamente, segun tambien
		 * lo que recibamos del metodo insertOnlyData. Porque si se recibe un false es que algo puede haber
		 * ido mal.
		 */
		
		// Se usara operador ternario por cambiar un poco
		System.out.println(resultBD ? "\nEl dato se ha actualizado correctamente en la base de datos!!":"\nNo se ha podido actualizar el dato correctamente");
				
	} // cierre requestData()
	
	/**
	 * Metodo bastante similar al anterior pero esa pensado solo para que el usuario administrador
	 * pueda realizar el cambio sobre el usuario en la base de datos que el desee. Para este metodo, con
	 * respecto al anterior se ha eliminado la opcion de borrar el usuario ya que esta parte va en otro
	 * metodo. No dentro del mismo. Tambien se ha modificado el texto para mostrar el menu al usuario
	 */
	
	public void requestDataForAdmin() {
		boolean resultBD = false;
		
		// Consideraciones y politicas de seguridad y validacion para registrar el dato correctamente
		System.out.println(Config.CONSIDERATIONS_SIGNUP2);
		
		// Aqui van los mensajes al usuario para elegir el campo que quiere modificar
		System.out.println(Config.MENU_UPDATE_DATA_USER2);

		int option = Interface.getInt("\n¿Que dato quieres cambiar?: ");
		
		switch (option) {
			case 1:
				// Pedimos nombre
				String name = Interface.getString("\nIntroduce nombre: ");
				// Validamos el dato
				if(!Utils.validateName(name)) {
					System.out.println("\nNombre y/o apellidos no validos");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(name, 1, this.getUsername());
				/**
				 * La siguiente linea de codigo tambien ha cambiado con respecto al anterior
				 * metodo. Le pasamos mediante set(), el campo en cuestion que se va a modificar
				 * a la variable user privada propia de la clase Admin. Haremos lo mismo para los
				 * demas campos
				 */
				Admin.getUser().setName(name);
				break;
			case 2:
				// Pedimos contrasenia
				String pass = Interface.getString("\nIntroduce nueva contraseña: ");
				// Validamos el dato
				if(!Utils.validatePassword(pass)) {
					System.out.println("\nContraseña NO valida");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(pass, 2, this.getUsername());
				break;
			case 3:
				// Pedimos nif
				String nif = Interface.getString("\nIntroduce NIF: ");
				// Validamos el dato
				if(!Utils.validateNif(nif)) {
					System.out.println("\nNombre y/o apellidos no validos");
					return;
				}
				if(Database.checkNif(nif)) {
					System.out.println("\nEl NIF que has introducido ya existe en la base de datos.\nDebes hacer uso de otro.");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(nif, 3, this.getUsername());
				Admin.getUser().setNif(nif);
				break;
			case 4:
				// Pedimos email
				String email = Interface.getString("\nIntroduce email: ");
				// Validamos el dato
				if(!Utils.validateEmail(email)) {
					System.out.println("\nEMAIL introducido NO valido");
					return;
				}
				if(Database.checkEmail(email)) {
					System.out.println("\nEl email que has introducido ya existe en la base de datos.\nDebes usar otro.");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(email, 4, this.getUsername());
				Admin.getUser().setEmail(email);
				break;
			case 5:
				// Pedimos direccion
				String address = Interface.getString("\nIntroduce la direccion: ");
				resultBD = database.insertOnlyData(address, 5, this.getUsername());
				Admin.getUser().setAddress(address);
				break;
			case 6:
				// Pedimos fecha de nacimiento
				String birthdate = Interface.getString("\nIntroduce fecha de nacimiento: ");
				// Validamos el dato
				if(!Utils.validateDate(birthdate)) {
					System.out.println("\nFecha de nacimiento NO valida");
					return;
				}
				// Lo mandamos al metodo en la clase Database.java que lo inserta en la BD
				resultBD = database.insertOnlyData(birthdate, 6, this.getUsername());
				Admin.getUser().setBirthdate(birthdate);
				break;
			default:
				System.out.println("\nNumero no valido.");
		} // cierre switch
		
		System.out.println(resultBD ? "\nEl dato se ha actualizado correctamente en la base de datos!!":"\nNo se ha podido actualizar el dato correctamente");
				
	} // cierre requestDataForAdmin()
	
	
} // cierre class
