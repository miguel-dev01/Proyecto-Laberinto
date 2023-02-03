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
	private int startI, startJ, endI, endJ;
	
	public Maze() { // Constructor de la clase Maze
		this.loaded = false;
		
	}
	
	public void loadMaze() {
		
		System.out.println("\nSelecciona el fichero que contiene el laberinto que deseas cargar: ");
		File[] files = showFilesinFolder();
		int number = Interface.getInt("\nSeleccione numero: ");
		
		// Restriccion para que no se pueda introducir un numero que no corresponda a ningun fichero/laberinto
		if(number <= 0 || number > files.length) {
			System.err.println("\nEl numero que has indicado no corresponde a ningun laberinto. Intentelo de nuevo.");
			return;
		}
		
		// Se seleccionara el nombre del fichero (junto con la ruta) que coincida con la posicion del 
		// array file(es en este array donde se recorre la carpeta mazes y se guarda cada nombre de un fichero en una
		// posicion del array files y lo devuelve llamando al metodo) del numero introducido, y asi es como se cargara
		this.filename = files[number-1].toString();

		if (files != null) {
			this.loaded = true;
			System.out.println("\nEL LABERINTO SELECCIONADO HA SIDO CARGADO CORRECTAMENTE!");
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
		
		// BufferedReader es una clase de Java que permite leer texto de ficheros o archivos.
		// Dentro del constructor de la clase BufferedReader llamamos a otra clase llamada FileReader, esta ultima
		// hara llamar al fichero a leer pasandole la ruta de este (filename).
		// Nota: Se usa try-with en lugar de try-catch, porque el uso de BufferedReader da muchas excepciones.
		// y siempre deben cerrarse correctamente los recursos despues de leer los ficheros.

		// COMENTARIOS... TODO
		try (BufferedReader file_length = new BufferedReader(new FileReader(this.filename))) {
			int rows_length = 0;
			int cols_length = 0;
			String line_length;
			while ((line_length = file_length.readLine()) != null) {
				rows_length++;
				// Con Math.max, al final de bucle se obtendra la longitud de la linea mas
				// larga. Y por tanto, el numero de
				// columnas que contiene el laberinto.
				cols_length = Math.max(cols_length, line_length.length());
			}
			// Lo inicializamos
			this.map = new char[rows_length][cols_length];
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
		// El metodo (readLine()), que es usado para leer cada linea del fichero, es perteneciente a la clase BufferedReader
		try (BufferedReader file = new BufferedReader(new FileReader(this.filename))){
			String line;
			int i = 0;
			
			while ((line = file.readLine()) != null) {
				
				for (int j = 0; j < line.length(); j++) {
					this.map[i][j] = line.charAt(j);
					System.out.print(this.map[i][j] + " ");
				}
				System.out.println();
				
				i++;
			}
			
		} catch (IOException e) {
			System.err.print("\nERROR. No se ha podido leer correctamente las lineas del laberinto.");
		}
		
	} // cierre showMap()
	
	public void setEntranceExit() {
		
		// Restriccion para que no se puedan introducir
		// coordenadas sin haber pasado por la opcion numero 1 de seleccionar el laberinto
		if(!this.loaded) {
			System.err.println("\nAntes de mostrar un laberinto debes seleccionarlo.");
			return;
		}
		
		
		System.out.print("\nIntroduce las coordenadas para la casilla de ENTRADA ");
			startJ = Interface.getInt("\n\tIntroduza numero de columna: ");
			startI = Interface.getInt("\n\tIntroduza numero de fila: ");
			
			map[startI][startJ] = 'E';
			System.out.print(map[startI][startJ]);
			for(int i = 0; i < this.map.length; i++) {
				for(int j = 0; j < this.map.length; j++) {
					System.out.print(this.map[i][j]);
					
				}
				
				System.out.println();
			}
			
		
		System.out.print("\nIntroduce las coordenadas para la casilla de SALIDA ");
			endJ = Interface.getInt("\n\tIntroduza numero de columna: ");
			endI = Interface.getInt("\n\tIntroduza numero de fila: ");
		
		map[endI][endJ] = 'S';
		
		//System.out.println(map[startI][startJ]);
		//System.out.println(map[endI][endJ]);
			
	} // cierre setEntranceExit()
	
	
	
} // cierre class Maze