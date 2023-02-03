/**
 * Main.java
 * Programa principal del sistema para resolver un laberinto
 * MSG - 2023.01.26
 * version 0.2.0
 */
import Classes.Config;
import Classes.Interface;
import Classes.Session;
import Classes.Maze;

public class Main {
	// Llamamos al constructor de Session  que contiene el login que verifica nombre de usuario y contraseņa
	public static Session meeting = new Session();
	
	// Llamamos al constructor de la clase Maze
	public static Maze labyrinth = new Maze();
	
	public static void main(String[] args) {
		System.out.println(Config.WELCOME);
		
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
				System.out.println("PROXIMAMENTE...");
				break;
			case 5:
				meeting.showUser();
				break;
			case 6:
				meeting.Logout();
				break;
				
		}
		
		
	} // Cierre Metodo loggedAction()
	
	
}