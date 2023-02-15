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
		//startI = 1;
		//startJ = 1;
	}

	
	public void loadMaze() {
		
		System.out.println("\nSelecciona el fichero que contiene el laberinto que deseas cargar: ");
		File[] files = showFilesInFolder();
		System.out.println("0 - Cancelar");
		int number = Interface.getInt("\nSeleccione numero: ");
		
		if ((number)-1 == -1) {
			return;
		}
		
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
			for(int j = 0; j < this.map[i].length; j++) {
				System.out.print(" " + this.map[i][j]);
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
		// o salida establecidas, a la hora de establecer nuevas coordenadas para casilla de entrada y salida.
		// Quedara por tanto, de nuevo en blanco donde corresponda.
		if(map[startI][startJ] == 'E') {
			map[startI][startJ] = ' ';
		}
		
		if(map[endI][endJ] == 'S') {
			map[endI][endJ] = ' ';
		}
		
		System.out.println("\nIntroduce las coordenadas para la casilla de ENTRADA -->");
			try {
				startI = Interface.getInt("\tIntroduce numero para la fila (entrada): ");
				startJ = Interface.getInt("\tIntroduce numero para la columna (entrada): ");
				
				// Llamada al metodo que contiene la logica para NO introducir la casilla de entrada sobre una
				// de las paredes del laberinto.
				restrictionsEntrance();
			}catch(IndexOutOfBoundsException e) {
				System.err.print("\nERROR. Has indicado las coordenadas fuera del laberinto. Intentelo de nuevo\n");
				startI = 0;
				startJ = 0;
				return;
	// Las variables de startI y startJ se dejan a 0 en caso de entrar a la excepcion, ya que si no, al entrar de
	// nuevo en setEntranceExit se dara la excepcion otra vez ya que dichas variables se quedaron con los valores
	// introducidos anteriormente que estaba fuera del laberinto.
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
			try {
				endI = Interface.getInt("\tIntroduce numero para la fila (salida): ");
				endJ = Interface.getInt("\tIntroduce numero para la columna (salida): ");
			
				// Llamada al metodo que recoge la logica para NO introducir la casilla de salida sobre una de las
				// paredes del laberinto y tampoco poder introducir la casilla de salida sobre la misma casilla
				// que la de entrada.
				restrictionsExit();
			}catch(IndexOutOfBoundsException e) {
				System.err.print("\nERROR. Has indicado las coordenadas fuera del laberinto. Intentelo de nuevo\n");
				endI = 0;
				endJ = 0;
				return;
				// Las variables de endI y endJ se dejan a 0 en caso de entrar a la
				// excepcion, ya que si no, al entrar de
				// nuevo en setEntranceExit se dara la excepcion otra vez ya que dichas
				// variables se quedaron con los valores
				// introducidos anteriormente que estaba fuera del laberinto.
			}
			
			
		// Estos dos bucles "crean" la casilla de SALIDA en el laberinto.
		for (int i = startI; i < this.map.length; i++) {
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
	
	private void restrictionsEntrance() {
		// Condicion para que NO se pueda introducir la casilla de entrada sobre una 
		// almohadilla del laberinto.
		boolean wallMaze = false;
		
		if (map[startI][startJ] == '#') {
			wallMaze = true;
		}

		while (wallMaze == true) {
			System.out.println("\nCoordenadas no validas."
					+ " La casilla de ENTRADA que has indicado no puede ir sobre una de las paredes del laberinto."
					+ " Intentelo de nuevo.");
			startI = Interface.getInt("\n\tIntroduce numero para la fila (entrada): ");
			startJ = Interface.getInt("\n\tIntroduce numero para la columna (entrada): ");

			if (map[startI][startJ] != '#') {
				wallMaze = false;
			}

		}
		
	} // cierre restrictionsEntrance();
	
		
	private void restrictionsExit() {
		// Condicion para que NO se pueda introducir la casilla de salida sobre una 
		// almohadilla del laberinto.
		// Y al mismo tiempo se este comprobando que la casilla de salida no esta sobre la de entrada
		// Debido a una serie de condiciones en el bucle while conseguimos que mientras, entre a la restriccion
		// de NO introducir las coordenadas de salida sobre una pared del laberinto, que el usuario tampoco 
		// pueda introducir al mismo tiempo las coordendas de la casilla de salida sobre la de entrada. Y viceversa.
		boolean wallMaze = false;
		boolean equalES = false;
		if(map[endI][endJ] == '#') {
			wallMaze = true;
		}
		
		if(map[endI][endJ] == map[startI][startJ]) {
			equalES = true;
		} 
		
		while (wallMaze == true || equalES == true) {
			System.out.println("\nCoordenadas no validas."
					+ " Puede que estes introduciendo la casilla de SALIDA sobre una de las paredes del laberinto. "
					+ "\nO puede que estes sobreponiendo la casilla de salida sobre la de entrada. Intentelo de nuevo.");
			endI = Interface.getInt("\n\tIntroduce numero para la fila (salida): ");
			endJ = Interface.getInt("\n\tIntroduce numero para la columna (salida): ");
			
			if(map[endI][endJ] != '#') {
				wallMaze = false;
			}
			
			if(map[endI][endJ] == map[startI][startJ]) {
				equalES = true;
			}
			
			if(map[endI][endJ] != map[startI][startJ]) {
				equalES = false;
			} 
			
			if (map[endI][endJ] == '#') {
				wallMaze = true;
			}
			
		}
		
	} // cierre restrictionsExit();	
	
	
	
	
	
} // cierre class Maze