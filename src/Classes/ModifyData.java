package Classes;

/** TODO Comentar
 * Esta clase intenta ser lo mas flexible posible de cara a 
 * las posibilidades que se le ofrece al usuario
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
	
		// Aqui van los mensajes al usuario para elegir el campo que quiere modificar
		System.out.println(Config.UPDATE_DATA_USER);
		
		int option = Interface.getInt("\n¿Que dato quieres cambiar?: ");
		
		switch (option) {//TODO PONER CONSIDERACIONES PARA CADA APARTADO
			case 1:
				// Pedimos nombre
				String name = Interface.getString("\nIntroduce nombre: ");
				database.insertOnlyData(name, 1, this.getUsername());
				break;
			case 2:
				// Pedimos contrasenia
				String pass = Interface.getString("\nIntroduce nueva contraseña: ");
				database.insertOnlyData(pass, 2, this.getUsername());
				break;
			case 3:
				// Pedimos nif
				String nif = Interface.getString("\nIntroduce NIF: ");
				database.insertOnlyData(nif, 3, this.getUsername());
				
				break;
			case 4:
				// Pedimos email
				String email = Interface.getString("\nIntroduce email: ");
				database.insertOnlyData(email, 4, this.getUsername());
				break;
			case 5:
				// Pedimos direccion
				String address = Interface.getString("\nIntroduce la direccion: ");
				database.insertOnlyData(address, 5, this.getUsername());
				break;
			case 6:
				// Pedimos fecha de nacimiento
				String birthdate = Interface.getString("\nIntroduce fecha de nacimiento: ");
				database.insertOnlyData(birthdate, 6, this.getUsername());
				break;
			default:
				System.out.println("\nNumero no valido.");
		} // cierre switch
		
		// TODO Poner aqui si la insercion ha sido correcta
				
	} // cierre requestData()
	
} // cierre class
