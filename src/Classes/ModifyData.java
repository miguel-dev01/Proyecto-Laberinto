package Classes;

/** TODO Comentar
 * Esta clase intenta ser lo mas flexible posible de cara a 
 * las posibilidades que se le ofrece al usuario
 */

public class ModifyData {

	private Database database = new Database();
	private String username;
	private boolean insertionResult;

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
				insertionResult = database.insertOnlyData(name, 1, this.getUsername());
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
				insertionResult = database.insertOnlyData(pass, 2, this.getUsername());
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
				insertionResult = database.insertOnlyData(nif, 3, this.getUsername());
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
				insertionResult = database.insertOnlyData(email, 4, this.getUsername());
				break;
			case 5:
				// Pedimos direccion
				String address = Interface.getString("\nIntroduce la direccion: ");
				insertionResult = database.insertOnlyData(address, 5, this.getUsername());
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
				insertionResult = database.insertOnlyData(birthdate, 6, this.getUsername());
				break;
			case 7:
				// Pedimos contrasenia del usuario para confirmar y borrar el usuario
				String password = Interface.getString("\nConfirma la contraseña para borrar tu usuario: ");
				
				insertionResult = database.deleteUserDB(this.getUsername(), Utils.encryptMd5(password));
				if(insertionResult) {
					System.out.println("\n\tUsuario eliminado exitosamente.");
					return;
				} else {
					System.err.println("\n\tNo se ha podido eliminar el usuario.");
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
		System.out.println(this.insertionResult ? "\nEl dato se ha actualizado correctamente en la base de datos!!":"\nNo se ha podido actualizar el dato correctamente");
				
	} // cierre requestData()
	
} // cierre class
