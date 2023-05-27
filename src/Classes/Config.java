package Classes;

public class Config {
	
	public static final String VERSION = "v1.2.0";
	
	//public static final String FILE_PATH = "assets/files/";
	
	public static final String MAZES_PATH = "assets/mazes/";
	
	//public static final String USERS_FILE = "users.txt";
	
	public static final String WELCOME = "Bienvenido al programa del laberinto. Por Miguel Sanchez (" + VERSION + ")\n"
			+ "Este programa calculara una ruta entre las casillas de entrada y salida de un laberinto cualquiera\n\n"
			+ "$$\\                 $$\\                           $$\\             $$\\                        $$$$$\\                                \r\n"
			+ "$$ |                $$ |                          \\__|            $$ |                       \\__$$ |                               \r\n"
			+ "$$ |       $$$$$$\\  $$$$$$$\\   $$$$$$\\   $$$$$$\\  $$\\ $$$$$$$\\  $$$$$$\\    $$$$$$\\              $$ | $$$$$$\\  $$\\    $$\\  $$$$$$\\  \r\n"
			+ "$$ |       \\____$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ $$ |$$  __$$\\ \\_$$  _|  $$  __$$\\             $$ | \\____$$\\ \\$$\\  $$  | \\____$$\\ \r\n"
			+ "$$ |       $$$$$$$ |$$ |  $$ |$$$$$$$$ |$$ |  \\__|$$ |$$ |  $$ |  $$ |    $$ /  $$ |      $$\\   $$ | $$$$$$$ | \\$$\\$$  /  $$$$$$$ |\r\n"
			+ "$$ |      $$  __$$ |$$ |  $$ |$$   ____|$$ |      $$ |$$ |  $$ |  $$ |$$\\ $$ |  $$ |      $$ |  $$ |$$  __$$ |  \\$$$  /  $$  __$$ |\r\n"
			+ "$$$$$$$$\\ \\$$$$$$$ |$$$$$$$  |\\$$$$$$$\\ $$ |      $$ |$$ |  $$ |  \\$$$$  |\\$$$$$$  |      \\$$$$$$  |\\$$$$$$$ |   \\$  /   \\$$$$$$$ |\r\n"
			+ "\\________| \\_______|\\_______/  \\_______|\\__|      \\__|\\__|  \\__|   \\____/  \\______/        \\______/  \\_______|    \\_/     \\_______|";
	
	public static final String MESSAGE_NEWFUNCTIONS = "\n\nNota: Esta nueva version (" + VERSION + ") del programa incorpora una variedad de nuevas funcionalidades para el usuario. \nComo por ejemplo:\n- Log del sistema\n- Modificacion de datos del usuario\n- Gestion de usuarios para el usuario administrador";
	
	public static final String GOODBYE = "\nFIN DEL PROGRAMA DEL LABERINTO. GRACIAS POR PARTICIPAR.\n";
	
	public static final String UNLOGGED_MENU = "\nMENU PRINCIPAL\n------------------\n1 - Iniciar sesion\n2 - Registro\n0 - Salir\n\n Seleccione una opcion: ";
	
	public static final String LOGGED_MENU = "\nMENU DE USUARIO\n-----------------\n1 - Cargar laberinto\n2 - Ver laberinto actual\n3 - Establecer casillas de entrada y salida\n4 - Buscar caminos\n5 - Ver usuario actual\n6 - Cerrar sesion\n7 - Modificar datos de usuario\n0 - Salir\n\n Seleccione una opcion: ";
	
	public static final String LOGGED_MENU_FOR_ADMIN = "\nMENU DE USUARIO\n-----------------\n1 - Cargar laberinto\n2 - Ver laberinto actual\n3 - Establecer casillas de entrada y salida\n4 - Buscar caminos\n5 - Ver usuario actual\n6 - Cerrar sesion\n7 - Modificar datos de usuario\n8 - Menu de superusuario\n0 - Salir\n\n Seleccione una opcion: ";
	
	public static final String MAZE_PATHS_MENU = "\nSELECCION DE CAMINOS\n---------------------------------\n1 - El primer camino posible\n2 - El camino mas corto\n0 - Cancelar";
	
	public static final String CONSIDERATIONS_SIGNUP = "\nCONSIDERACIONES PARA REGISTRAR CORRECTAMENTE UN USUARIO\n-----------------------------------------------------------"
			+ "\n\nNOMBRE DE USUARIO\n"
			+ "Debe contener entre 0 y 20 caracteres."
			+ "\n\nContraseña\n"
			+ "Debe contener mas de 8 caracteres, minusculas, mayusculas, numeros y algun caracter especial."
			+ "\n\nNOMBRE\n"
			+ "Debe empezar por letra mayuscula, tener como minimo dos caracteres y tan solo contener letras."
			+ "\n\nNIF\n"
			+ "Debe tener una longitud de 9 caracteres, los 8 primeros seran numeros y el ultimo caracter debe ser una letra (minuscula o mayus)"
			+ "\n\nEMAIL\n"
			+ "Debe seguir la estructura de un email para que sea valido. Ejemplo: nombre_usuario@dominio.es"
			+ "\n\nFECHA NACIMIENTO\n"
			+ "Debe tener la estructura correcta de una fecha en formato dd/mm/aaaa.\n"
			+ "\nIntroduce tus datos a continuacion"
			+ "\n\t|"
			+ "\n\t|"
			+ "\n\tV";
	
	public static final String CONSIDERATIONS_SIGNUP2 = "\nRecuerde las CONSIDERACIONES para ACTUALIZAR los datos correctamente:\n-----------------------------------------------------------"
			+ "\n\nNOMBRE DE USUARIO\n"
			+ "Debe contener entre 0 y 20 caracteres."
			+ "\n\nContraseña\n"
			+ "Debe contener mas de 8 caracteres, minusculas, mayusculas, numeros y algun caracter especial."
			+ "\n\nNOMBRE\n"
			+ "Debe empezar por letra mayuscula, tener como minimo dos caracteres y tan solo contener letras."
			+ "\n\nNIF\n"
			+ "Debe tener una longitud de 9 caracteres, los 8 primeros seran numeros y el ultimo caracter debe ser una letra (minuscula o mayus)"
			+ "\n\nEMAIL\n"
			+ "Debe seguir la estructura de un email para que sea valido. Ejemplo: nombre_usuario@dominio.es"
			+ "\n\nFECHA NACIMIENTO\n"
			+ "Debe tener la estructura correcta de una fecha en formato dd/mm/aaaa.\n";
	
	public static final String MENU_UPDATE_DATA_USER = "\nMODIFICACION DE DATOS DE USUARIO\n---------------------------------\nElige una:\n1 - Nombre\n2 - Contraseña\n3 - NIF\n4 - Email\n5 - Address\n6 - Birthdate\n7 - Eliminar usuario actual";
	
	public static final String MENU_UPDATE_DATA_USER2 = "\nMODIFICACION DE DATOS DE USUARIO\n---------------------------------\nElige una:\n1 - Nombre\n2 - Contraseña\n3 - NIF\n4 - Email\n5 - Address\n6 - Birthdate";
	
	public static final String MENU_USERADMIN = "\nMENU DE SUPERUSUARIO\n------------------------\nElige una:\n1 - Crear nuevo usuario\n2 - Ver usuario\n3 - Modificar datos del usuario\n4 - Eliminar usuario";
			
	/**
	 *  Dado que estas variables son estaticas no es necesario crear objetos de esta clase, sino simplemente
	 *  con llamar a la propiedad ya seria suficiente.
	 */
}