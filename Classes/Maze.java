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
	private int StartI;
	private int StartJ;
	private int endI;
	private int endJ;
	//private int guardar;
	
	public Maze() {
		this.loaded = false;
		this.map = new char[100][100];
		
	}
	
	public int cargar() {
		int i = 0;
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(this.filename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			String line;
			while ((line = file.readLine()) != null) {
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
	public void loadMaze() {
		
		System.out.println("\nSelecciona el fichero que contiene el laberinto que deseas cargar: ");
		showFilesinFolder();
		int number = Interface.getInt("\nSeleccione numero: ");
		
		// Se seleccionara el nombre del fichero (junto con la ruta) que coincida con la posicion del 
		// array file(es en este array donde se recorre la carpeta mazes y se guarda cada nombre de un fichero en una
		// posicion del array files y lo devuelve llamando al metodo) del numero introducido, y asi es como se cargara
		File[] file = showFilesinFolder();
		this.filename = file[number-1].toString();
		
		if (showFilesinFolder() != null) {
			this.loaded = true;
			System.out.println("\nEL LABERINTO HA SIDO CARGADO CORRECTAMENTE");
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
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(new File(this.filename)));
		}catch(FileNotFoundException e) {
			System.err.print("\nNo se ha podido leer correctamente el fichero pasado por parametro.");
		}
		
		// Ahora lo que debemos hacer para mostrar el laberinto y almacenarlo en un array de tipo char bidimensionales
		// (map) es crear una variable String (line) que almacena cada linea del fichero leido por BufferedReader
		// Cada linea se ira recorriendo con un bucle while y mientras la linea sea diferente de null, es decir, que
		// contenga algun caracter, pues se seguira iterando y guardando cada linea en el string line.
		// Despues cada una de las lineas se almacena como char, con el metodo charAt, siendo J la posicion de cada
		// caracter, y por tanto cada elemento de la linea. I sera cada linea del fichero.
		// El metodo (readLine()) usado para leer cada linea del fichero es perteneciente a la clase BufferedReader 
		try {
			String line;
			int i = 0;
			while ((line = file.readLine()) != null) {
				
				for (int j = 0; j < line.length(); j++) {
					this.map[i][j] = line.charAt(j);
					System.out.print(this.map[i][j]);
				}
				System.out.println();
				
				i++;
			}
			
			//guardar = i;

		} catch (IOException e) {
			System.err.print("\nHa habido un error al leer la linea");
		}
		
	} // cierre showMap()
	
	public void setEntranceExit() {
		
		System.out.print("\nIntroduce las coordenadas para la casilla de ENTRADA ");
			StartJ = Interface.getInt("\tIntroduza numero de columna: ");
			StartI = Interface.getInt("\tIntroduza numero de fila: ");
			
		map[StartI][StartJ] = 'E';
		
		// casilla de Salida TODO
		
		
	} // cierre setEntranceExit()
	
	
	
} // cierre class Maze