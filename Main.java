/**
 * Main.java
 * Programa principal del sistema para resolver un laberinto
 * MIGUEL SANCHEZ GARCIA - 2023.03.29
 * version 1.1.0
 */
import Classes.Config;
import Classes.Interface;
import Classes.Session;
import Classes.Maze;

public class Main {
	// Llamamos al constructor de la clase Session, dicha clase contiene la logica de login que verifica 
	// nombre de usuario y contraseña, asi como registro de un nuevo usuario
	public static Session meeting = new Session();
	
	// Llamamos al constructor de la clase Maze que contiene lo necesario para cargar y mostrar laberinto.
	// Asi como añadir casillas de entrada y salida en el laberinto
	public static Maze labyrinth = new Maze();
	
	public static void main(String[] args) {
		// Mensaje de bienvenida
		System.out.println(Config.WELCOME);
		// Mensaje de nota con respecto a la implementacion de la BD
		System.out.println(Config.MESSAGE_BD);
		
		int option = 0;
		
		do {
			
			if(meeting.isLogged()) {
				option = Interface.getInt(Config.LOGGED_MENU);
				loggedAction(option);
			} else {
				option = Interface.getInt(Config.UNLOGGED_MENU);
				unloggedAction(option);
			}
			
		} while(option != 0);
		
		
		System.out.println(Config.GOODBYE);
		
	} // Cierre MAIN
	
	public static void unloggedAction(int option) {
		
		switch(option) {
			case 1:
				meeting.Login();
				break;
			case 2:
				System.out.println("\nREGISTRO\nIntroduce tus datos a continuacion:\n");
				meeting.signUp();
				break;
				
		}
		
		
	} // Cierre Metodo unloggedAction()
	
	
	public static void loggedAction(int option) {
		
		switch(option) {
			case 1:
				labyrinth.loadMaze();
				break;
			case 2:
				System.out.println();
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
				meeting.Logout();
				break;
				
		}
		
		
	} // Cierre Metodo loggedAction()
	
	
}