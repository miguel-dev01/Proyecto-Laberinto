package Classes;

public class Config {
	
	public static final String VERSION = "v1.0.0";
	
	public static final String FILE_PATH = "assets/files/";
	
	public static final String MAZES_PATH = "assets/mazes/";
	
	public static final String USERS_FILE = "users.txt";
	
	public static final String WELCOME = "Bienvenido al programa del laberinto. Por Miguel Sanchez (" + VERSION + ")\n"
			+ "Este programa calculara una ruta entre la entrada y salida de un laberinto cualquiera\n\n"
			+ "$$\\                 $$\\                           $$\\             $$\\                        $$$$$\\                                \r\n"
			+ "$$ |                $$ |                          \\__|            $$ |                       \\__$$ |                               \r\n"
			+ "$$ |       $$$$$$\\  $$$$$$$\\   $$$$$$\\   $$$$$$\\  $$\\ $$$$$$$\\  $$$$$$\\    $$$$$$\\              $$ | $$$$$$\\  $$\\    $$\\  $$$$$$\\  \r\n"
			+ "$$ |       \\____$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ $$ |$$  __$$\\ \\_$$  _|  $$  __$$\\             $$ | \\____$$\\ \\$$\\  $$  | \\____$$\\ \r\n"
			+ "$$ |       $$$$$$$ |$$ |  $$ |$$$$$$$$ |$$ |  \\__|$$ |$$ |  $$ |  $$ |    $$ /  $$ |      $$\\   $$ | $$$$$$$ | \\$$\\$$  /  $$$$$$$ |\r\n"
			+ "$$ |      $$  __$$ |$$ |  $$ |$$   ____|$$ |      $$ |$$ |  $$ |  $$ |$$\\ $$ |  $$ |      $$ |  $$ |$$  __$$ |  \\$$$  /  $$  __$$ |\r\n"
			+ "$$$$$$$$\\ \\$$$$$$$ |$$$$$$$  |\\$$$$$$$\\ $$ |      $$ |$$ |  $$ |  \\$$$$  |\\$$$$$$  |      \\$$$$$$  |\\$$$$$$$ |   \\$  /   \\$$$$$$$ |\r\n"
			+ "\\________| \\_______|\\_______/  \\_______|\\__|      \\__|\\__|  \\__|   \\____/  \\______/        \\______/  \\_______|    \\_/     \\_______|";
	
	public static final String GOODBYE = "\n\nFIN DEL PROGRAMA DEL LABERINTO. GRACIAS POR PARTICIPAR.\n\n";
	
	public static final String UNLOGGED_MENU = "\nMENU PRINCIPAL\n------------------\n1 - Iniciar sesion\n2 - Registro\n0 - Salir\n\n Seleccione una opcion: ";
	
	public static final String LOGGED_MENU = "\nMENU DE USUARIO\n-----------------\n1 - Cargar laberinto\n2 - Ver laberinto actual\n3 - Establecer casillas de entrada y salida\n4 - Buscar caminos\n5 - Ver usuario actual\n6 - Cerrar sesion\n0 - Salir\n\n Seleccione una opcion: ";
	
	public static final String MAZE_PATHS_MENU = "\nSELECCION DE CAMINOS\n---------------------------------\n1 - El primer camino posible\n2 - El camino mas corto\n0 - Cancelar";
			
	// Dado que estas variables son estaticas no es necesario crear objetos de esta clase, sino simplemente
	// con llamar a la propiedad ya seria suficiente.
}