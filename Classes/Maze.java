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
		File[] files = showFilesInFolder();
		int number = Interface.getInt("\nSeleccione numero: ");
		
		if(number <= 0 || number > files.length) {
			System.err.println("\nEl numero que has indicado no corresponde a ningun laberinto. Intentelo de nuevo.");
			return;
		}
		
		// Obtenemos el nombre del fichero .txt que contiene el laberinto
		this.filename = files[number-1].getName();
		
			try (BufferedReader file = new BufferedReader(new FileReader(Config.MAZES_PATH + this.filename))){
				
				// La siguiente llamada al metodo arrayMapInit() inicializa la matriz de map segun la cantidad de lineas
				// y columnas que haya en el laberinto del fichero seleccionado antes de comenzar a guardar dicho laberinto
				// en este. (array)
				arrayMapInit();
				
				String line;
				int i = 0;
				// El siguiente bucle while almacena todo lo que contiene el fichero en el array bidimensional map, segun
				// la linea (i) de dicho fichero, asi como cada caracter de esta (j)
				// Y cuando se haga la llamada al metodo showMap(), se mostrara lo que se ha almacenado en el array Map
				while ((line = file.readLine()) != null) {
					
					for (int j = 0; j < line.length(); j++) {
						this.map[i][j] = line.charAt(j);
					}
					i++;
				}
				
			// Lo siguiente cambia la variable de loaded del laberinto a true, cuando el bucle while haya finalizado,
			// para que este se pueda mostrar en los siguiente metodos. No se hace condicion dado que al estar en un try,
			// en caso de que hubiera algun error mientra que se guardan los caracteres del fichero en el array, iria
			// inmediatamente al catch. Por lo que daria una excepcion y loaded no cambiaria a true.
				this.loaded = true;
				System.out.println("\nEL LABERINTO SELECCIONADO HA SIDO CARGADO CORRECTAMENTE!");

			} catch (FileNotFoundException e) {
				System.err.println("\nError: Fichero/laberinto no encontrado.");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("\nError mientras se accedia a la informacion del laberinto.");
				e.printStackTrace();
			}
			
			
	} // cierre loadMaze()
	
	
	public void showMap()  {
		
		// Restriccion para que no se pueda mostrar un laberinto sin haber pasado por la opcion numero 1 de seleccionarlo
		if(!this.loaded) {
			System.err.println("\nAntes de mostrar un laberinto debes seleccionarlo.");
			return;
		}
		
		for(int i = 0; i < this.map.length; i++) {
			for(int j = 0; j < this.map.length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
		

	} // cierre showMap()
	
	public void setEntranceExit() {
		
		// Restriccion para que no se puedan introducir
		// coordenadas sin haber pasado por la opcion numero 1 de seleccionar el laberinto
		if(!this.loaded) {
			System.err.println("\nAntes de mostrar un laberinto debes seleccionarlo.");
			return;
		}
		
		// En las siguientes dos condiciones, si se cumplen, para que NO puedan haber mas de una casilla de entrada
		// o salida establecidas, se quedara de nuevo en blanco donde corresponda.
		if(map[startI][startJ] == 'E') {
			map[startI][startJ] = ' ';
		}
		
		if(map[endI][endJ] == 'S') {
			map[endI][endJ] = ' ';
		}
		
		System.out.println("\nIntroduce las coordenadas para la casilla de ENTRADA -->");
			startI = Interface.getInt("\tIntroduce numero para la fila (entrada): ");
			startJ = Interface.getInt("\tIntroduce numero para la columna (entrada): ");
			
			// Restriccion para que en caso de que las coordenadas indicadas queden sobre una alhomadilla,
			// llame al metodo restrictionEntrance() y si devuelve un true, se volvera a llamar a este metodo
			// para que vuelva a introducir las coordenadas correctamente.
			boolean constraintEntrance = restrictionEntrance();
			if(constraintEntrance == true) {
				setEntranceExit();
			}
	
		// Estos dos bucles "crean" la casilla de ENTRADA en el laberinto.
		for(int i = 0; i < this.map.length; i++) {
			for(int j = 0; j < this.map.length; j++) {
				// Si esta condicion se cumple se marcara la casilla de entrada. Una vez cumplida se deja de iterar
				if (startI == i && startJ == j) {
					this.map[i][j] = 'E';
					break;
				}
			} // cierre bucle J (entrada)
		} // cierre bucle I (entrada)
		
		System.out.println("\nIntroduce las coordenadas para la casilla de SALIDA -->");
			endI = Interface.getInt("\tIntroduce numero para la fila (salida): ");
			endJ = Interface.getInt("\tIntroduce numero para la columna (salida): ");
			
			// Restriccion para que en caso de que las coordenadas indicadas queden sobre una alhomadilla,
			// llame al metodo restrictionExit() y si devuelve un true, se volvera a llamar a este metodo para que
			// vuelva a introducir las coordenadas correctamente.
			boolean constraintExit = restrictionExit();
			if(constraintExit == true) {
				setEntranceExit();
			}
			
		// Estos dos bucles "crean" la casilla de SALIDA en el laberinto.
		for (int i = startI + 1; i < this.map.length; i++) {
			for (int j = 0; j < this.map.length; j++) {
				// Si esta condicion se cumple se marcara la casilla de salida. Una vez cumplida se deja de iterar
				if (endI == i && endJ == j) {
					this.map[i][j] = 'S';
					break;
				}
			} // cierre bucle J (salida)
		} // cierre bucle I (salida)
			
		
	} // cierre setEntranceExit()
	
	// Metodo que recorre la carpeta mazes y cada nombre de fichero que encuentre lo almacenara en un array de tipo File
	// para poder seleccionarlo y cargar posteriormente el laberinto correspondiente de cada fichero en el metodo loadMaze().
	private File[] showFilesInFolder() {
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
		
	} // cierre showFilesInFolder()	
	
	
	// El siguiente metodo privado dimensiona el array Map antes de guardar el laberinto en el.
	private void arrayMapInit () {
	    try (BufferedReader file_length = new BufferedReader(new FileReader(Config.MAZES_PATH + this.filename))) {
	    	int length = 0;
	        
	        while ((file_length.readLine()) != null) {
	            length++;
	        }
	        
	        // Inicializamos el array
	        this.map = new char[length][length];
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	} // cierre arrayMapInit()
	
	// Falta tambien por hacer la condicion donde salga un mensaje de error en caso de introducir numero fuera del laberinto
	
	private boolean restrictionEntrance() {
		// Condicion para que NO se pueda introducir la casilla de entrada sobre una 
		// almohadilla del laberinto.
		if(map[startI][startJ] == '#') {
			System.err.println("\nCoordenadas no validas."
					+ " La casilla de ENTRADA que has indicado no puede ir sobre una de las paredes del laberinto."
					+ " Intentelo de nuevo.");
			return true;
		}
		return false;
	} // cierre restrictionEntrance();
		
	private boolean restrictionExit() {
		// Condicion para que NO se pueda introducir la casilla de salida sobre una 
		// almohadilla del laberinto.
		if(map[endI][endJ] == '#') {
			System.err.println("\nCoordenadas no validas."
					+ " La casilla de SALIDA que has indicado no puede ir sobre una de las paredes del laberinto."
					+ " Intentelo de nuevo.");
			return true;
		}
		return false;
	} // cierre restrictionExit();
	
		
		
		
	
	
	
	
	
} // cierre class Maze