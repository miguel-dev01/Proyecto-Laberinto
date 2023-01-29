package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
	
	private char[][] map;
	private String filename;
	private boolean loaded;
	private int startI;
	private int startJ;
	private int endI;
	private int endJ;
	
	public Maze() {
		this.loaded = false;
		
	}
	
	public void loadMaze() {
		
		System.out.println("\nSelecciona el fichero que contiene el laberinto que deseas cargar: ");
		File[] file = showFilesinFolder();
		int number = Interface.getInt("\nSeleccione numero: ");
		
		// TODO Restriccion para que no se pueda introducir un numero que no corresponda a ningun fichero
		
		// Se seleccionara el nombre del fichero (junto con la ruta) que coincida con la posicion del 
		// array file(es en este array donde se recorre la carpeta mazes y se guarda cada nombre de un fichero en una
		// posicion del array files y lo devuelve llamando al metodo) del numero introducido, y asi es como se cargara
		this.filename = file[number-1].toString();
		/*
		// inicializar array bi map
		try {
			BufferedReader file_length = new BufferedReader(new FileReader(this.filename));
		    // Obtener la primera l�nea del archivo para obtener el n�mero de filas
		    int rows = file_length.readLine().length();
		    // Obtener la segunda l�nea del archivo para obtener el n�mero de columnas
		    int cols = file_length.readLine().length();
		    // Inicializar el array bidimensional con las dimensiones obtenidas
		    map = new char[rows][cols];
		} catch(IOException e) {
		   System.err.print("\nERROR. El array no ha podido ser inicializado/dimensionado correctamente.");
		}
		*/
		if (file != null) {
			this.loaded = true;
			System.out.println("\nEL LABERINTO HA SIDO CARGADO CORRECTAMENTE!");
		}
		
	} // cierre loadMaze()
	
	// Metodo que recorre la carpeta mazes y cada nombre de fichero que encuentre lo almacenara en un array de tipo File
	private File[] showFilesinFolder() {
		File folder = new File(Config.MAZES_PATH);
		
		if (folder.exists() && folder.isDirectory()) {
			File[] file = folder.listFiles();
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					System.out.println((i+1) + " - " + file[i].getName());
				}
			}
			return file;
		} else {
			System.err.println("\nLa ruta especificada no es una carpeta valida");
			return null;
		}
		
	} // cierre showFilesinFolder()
	
	
	public void showMap()  {
		
		// Restriccion para que no se pueda mostrar un laberinto sin haber pasado por la opcion numero 1 de seleccionarlo
		if(!this.loaded) {
			System.err.println("\nAntes de mostrar un laberinto debes seleccionarlo.");
			return;
		}
		
		// BufferedReader es una clase de Java que permite leer texto de forma sencilla.
		// Dentro del constructor de la clase BufferedReader llamamos a otra clase llamada FileReader, esta ultima
		// hara llamar al fichero a leer pasandole la ruta de este (filename).
		// Nota: Se usa try-catch porque el uso de BufferedReader da muchas excepciones.

		// El siguiente try-with inicializa/dimensiona el array bidimensional de map, segun las filas y columnas del
		// laberinto seleccionado
		// try-with hace... TODO
		// TRY-WITH-RESOURCES HA SIDO LA CLAVE PARA QUE LA SIGUIENTE IMPLEMENTACION PUEDA FUNCIONAR
		// El gran dolor de cabeza estaba en que BufferedReader da muchas excepciones porque debe cerrarse al acabar
		// el programa. Y para que esto pasara debe ir envuelto en muchos try-catch, pero try-with lo hace mas comodo
		// y mas precisoa
        try (BufferedReader file_length = new BufferedReader(new FileReader(this.filename))) {
        	int rows_length = 0;
        	int cols_length = 0;
        	String line_length;
            while ((line_length = file_length.readLine()) != null) {
            	rows_length++;
            	// TODO Para la siguiente linea es mas eficiente introducirla un bucle while que se realice solo una vez.
            	cols_length = line_length.length();
            }
            // Lo inicializamos
            map = new char[rows_length][cols_length];
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Ahora lo que debemos hacer para mostrar el laberinto y almacenarlo en un array de tipo char bidimensionales
		// (map) es crear una variable String (line) que almacena cada linea del fichero leido por BufferedReader
		// Cada linea se ira recorriendo con un bucle while y mientras la linea sea diferente de null, es decir, que
		// contenga algun caracter, pues se seguira iterando y guardando cada linea en el string line.
		// Despues cada una de las lineas se almacena como char, con el metodo charAt, siendo J la posicion de cada
		// caracter, y por tanto cada elemento de la linea correspondiente. I sera cada linea del fichero.
		// El metodo (readLine()), usado para leer cada linea del fichero, es perteneciente a la clase BufferedReader
		try (BufferedReader file = new BufferedReader(new FileReader(this.filename))){
			String line;
			int i = 0;
			// meter bucle while line_length.readerLine() != null aqui
			while ((line = file.readLine()) != null) {
				
				for (int j = 0; j < line.length(); j++) {
					this.map[i][j] = line.charAt(j);
					System.out.print(this.map[i][j]);
				}
				System.out.println();
				
				i++;
			}
			
		} catch (IOException e) {
			System.err.print("\nERROR. No se ha podido leer correctamente las lineas del laberinto.");
		}
		
	} // cierre showMap()
	
	public void setEntranceExit() {
		
		System.out.print("\nIntroduce las coordenadas para la casilla de ENTRADA ");
			startJ = Interface.getInt("\n\tIntroduza numero de columna: ");
			startI = Interface.getInt("\n\tIntroduza numero de fila: ");
			
		map[startI][startJ] = 'E';
		
		System.out.print("\nIntroduce las coordenadas para la casilla de SALIDA ");
			endJ = Interface.getInt("\n\tIntroduza numero de columna: ");
			endI = Interface.getInt("\n\tIntroduza numero de fila: ");
		
		map[endI][endJ] = 'S';
		
		System.out.println(map[startI][startJ]);
		System.out.println(map[endI][endJ]);
			
	} // cierre setEntranceExit()
	
	
	
} // cierre class Maze