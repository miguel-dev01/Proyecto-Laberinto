package Classes;

public class Admin {

	private Database database = new Database();
	private static Session session = new Session();
	private String userAdmin;
	
	public String getUserAdmin() {
		return userAdmin;
	}
	public void setUserAdmin(String userAdmin) {
		this.userAdmin = userAdmin;
	}
	
	/**
	 * Metodo que muestra al usuario admin las opciones que tiene para hacer como administrador
	 */
	public static void showOptions() {
		
		System.out.println(Config.MENU_USERADMIN);
		int option = Interface.getInt("\n¿Que accion quieres realizar?: ");
		
		switch(option) {
			case 1:
				session.signUp();
				break;
			case 2:
				
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			default:
				System.out.println("\nNumero invalido");
		} // cierre switch
				
		
	} // cierre showOptions()
	
	
	
} // cierre class
