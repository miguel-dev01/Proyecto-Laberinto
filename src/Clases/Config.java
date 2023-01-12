package Clases;

public class Config {
	
	public static final String VERSION = "v0.1.0";
	
	public static final String FILE_PATH = ".\\assets\\files\\";
	
	public static final String USERS_FILE = "users.txt";
	
	// Se podria poner un dibujo
	public static final String WELCOME = "Bienvenido al programa del laberinto. Por Miguel Sanchez (" + VERSION + ")\n";
	
	public static final String GOODBYE = "\n\nFIN DEL PROGRAMA. GRACIAS POR PARTICIPAR.";
	
	public static final String UNLOGGED_MENU = "MENU PRINCIPAL\n---------------\n1 - Iniciar sesion\n2 - Registro\n0 - Salir\n\n Seleccione una opcion: ";
	
	public static final String LOGGED_MENU = "\nMENU DE USUARIO\n--------------\n1 - Cargar laberinto\n2 - Ver laberinto actual\n3 - Establecer casillas de entrada y salida\n4 - Buscar caminos\n5 - Ver usuario actual\n6 - Cerrar sesion\n0 - Salir\n\n Seleccione una opcion: ";
			
	// Dado que estas variables son estaticas no es necesario crear objetos de esta clase, sino simplemente
	// con llamar a la propiedad ya seria suficiente.
}