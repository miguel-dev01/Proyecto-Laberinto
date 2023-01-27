/**
 * Main.java
 * Programa principal del sistema para resolver un laberinto
 * MSG - 2023.01.11
 * version 0.1.0
 */
import Clases.Config;
import Clases.Interface;
import Clases.Session;


public class Main {
	// Llamamos al constructor de Sesion  que contiene el login que verifica nombre de usuario y contraseña
	public static Session meeting = new Session();
	
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
				System.out.println("PROXIMAMENTE...");
				break;
			case 2:
				System.out.println("PROXIMAMENTE...");
				break;
			case 3:
				System.out.println("PROXIMAMENTE...");
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