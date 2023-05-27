package Main;

/**
 * Main.java
 * Programa principal del sistema para resolver un laberinto
 * MIGUEL SANCHEZ GARCIA - 2023.04.27
 * version 1.2.0
 */
import Classes.Config;
import Classes.Database;
import Classes.Interface;
import Classes.Log;
import Classes.Maze;
import Classes.ModifyData;
import Classes.Session;
import Classes.Admin;

public class Main {
	/** Llamamos al constructor de la clase Session, dicha clase contiene la logica de login que verifica 
	 *  nombre de usuario y contraseña, asi como registro de un nuevo usuario...
	 */
	public static Session meeting = new Session();
	
	/**
	 *  Llamamos al constructor de la clase Maze que contiene lo necesario para cargar y mostrar laberinto.
	 *  Asi como añadir casillas de entrada y salida en el laberinto
	 */
	public static Maze labyrinth = new Maze();
	
	/**
	 * Objeto de la clase 'ModifyData' que pide los datos de usuario para modificarlos.
	 */
	public static ModifyData modifyDataUser = new ModifyData();
	
	/**
	 * Objeto de la clase Database que accede a la base de datos.
	 */
	public static Database db = new Database();
	
	public static void main(String[] args) {
		/**
		 * Metodo que crea el fichero de los eventos en caso de que no exista al iniciar la ejecucion del programa
		 */
		Log.createFile();
		
		/**
		 * Se guarda el evento de inicio del programa. Y asi por cada vez que queramos guardar un evento
		 */
		Log.loggerEvents("Inicio del programa", "Inicio sin errores");//Evento
		
		// Mensaje de bienvenida
		System.out.println(Config.WELCOME);
		// Mensaje de nota con respecto a la implementacion de las nuevas funcionalidades para esta version
		System.out.println(Config.MESSAGE_NEWFUNCTIONS);
		
		int option = 0;
		
		do {
			
			if(meeting.isLogged()) {
				if(db.showRol(meeting.user.getUsername()).equals("admin")) {
					option = Interface.getInt(Config.LOGGED_MENU_FOR_ADMIN);
				} else {
					option = Interface.getInt(Config.LOGGED_MENU);
				}
				loggedAction(option);
			} else {
				option = Interface.getInt(Config.UNLOGGED_MENU);
				unloggedAction(option);
			}
			
		} while(option != 0);
		
		// Mensaje de despedida
		System.out.println(Config.GOODBYE);
		
		Log.loggerEvents("Fin del programa", "Finalizacion sin errores");//Evento
	} // Cierre MAIN
	
	public static void unloggedAction(int option) {
		
		switch(option) {
			case 1:
				meeting.login();
				break;
			case 2:
				System.out.println("\nREGISTRO DE USUARIO\n");
				meeting.signUp();
				break;
			case 0: 
				System.out.print("");
				break;
			default:
				System.out.println("\nNumero invalido");
		}
		
	} // Cierre Metodo unloggedAction()
	
	
	public static void loggedAction(int option) {

		switch(option) {
			case 1:
				labyrinth.loadMaze();
				break;
			case 2:
				labyrinth.showMap();
				break;
			case 3:
				labyrinth.setEntranceExit();
				break;
			case 4:
				labyrinth.selectAlgorithm();
				break;
			case 5:
				meeting.showUser();
				break;
			case 6:
				labyrinth = new Maze();
				meeting.logout();
				break;
			case 7:
				/**
				 * Una vez estemos logueados se extraera el nombre de usuario, para identificar que 
				 * usuario esta actualmente con la sesion iniciada y asi saber para que registro
				 * de la base de datos debemos cambiar dicho dato
				 */
				modifyDataUser.setUsername(meeting.user.getUsername());
				/**
				 * Metodo que indica y pide al usuario que dato quiere cambiar
				 */
				modifyDataUser.requestData();
				break;
			case 8:
				if(db.showRol(meeting.user.getUsername()).equals("admin")) {
					Admin.showOptions();
				} else {
					System.err.println("\nOpcion no disponible");
				}
				break;
			case 0:
				System.out.print("");
				break;
			default:
				System.out.println("\nEl numero introducido no tiene asignado ninguna funcionalidad");
		}
		
	} // Cierre Metodo loggedAction()
}