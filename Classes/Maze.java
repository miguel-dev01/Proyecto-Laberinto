package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Maze { 
	
	private char[][] map;
	private String filename;
	private boolean loaded;
	private int startI, startJ, endI, endJ;
	private boolean defineES;
	private int counter_DFS;
	private boolean pathExists;
	private int numberMazeSelected;
	private ArrayList<Coordinate> path;
	
	
	public Maze() { // Constructor de la clase Maze
		this.loaded = false;
		this.defineES = false;
		this.path = new ArrayList<Coordinate>();
		this.counter_DFS = -1; // Esta variable se inicializa en -1, y al llamar al metodo DFS, se sumara mas uno,
								// y si dentro de este metodo(DFS) no se ha incrementado en las condiciones(if) significa que no se ha
								// hallado una solucion, quedando el counter en 0
		this.pathExists = false;
	}

	
	public void loadMaze() {
		
		System.out.println("\nSelecciona el fichero que contiene el laberinto que deseas cargar: ");
		File[] files = showFilesInFolder();
		System.out.println("0 - Cancelar");
		numberMazeSelected = Interface.getInt("\nSeleccione numero: ");
		
		// Cancelar
		if ((numberMazeSelected)-1 == -1) {
			return;
		}
		
		if(numberMazeSelected <= 0 || numberMazeSelected > files.length) {
			System.err.println("\nEl numero que has indicado no corresponde a ningun laberinto. Intentelo de nuevo.");
			return;
		}
		
		// Obtenemos el nombre del fichero .txt que contiene el laberinto
		this.filename = files[numberMazeSelected-1].getName();
		
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
		
		// Este while muestra por pantalla el camino recorrido desde 'E' hasta 'S'
		int c = 1;
		while(c < path.size()) {
			// A la hora de seleccionar un nuevo laberinto, para que no aparezca en consonancia
			// con el camino generado del laberinto (las flechas) si el anterior camino ha 
			// sido generado por completo, se eliminara justo despues de este bucle, y cuando entre de
			// nuevo a este bucle se comprobara si el array list path esta vacio, y si esta vacio,
			// no se imprime nada.
			if(path.isEmpty()) {
				break;
			}
			
			if(map[startI][startJ] == '*') {
				map[startI][startJ] = 'E';
			}
			
			if(path.get(c).direction == 0) {
				map[path.get(c).i][path.get(c).j] = 'E';
			}
			
			if (path.get(c).direction == 1) {
				map[path.get(c).i][path.get(c).j] = '<';
			}
			
			if (path.get(c).direction == 2) {
				map[path.get(c).i][path.get(c).j] = '^';
			}
			
			if (path.get(c).direction == 3) {
				map[path.get(c).i][path.get(c).j] = '>';
			}
			
			if (path.get(c).direction == 4) {
				map[path.get(c).i][path.get(c).j] = 'V';
			}
			
			c++;
		}
		
		// Si se cumple esta condicion, el array list se elimina
		if (c == path.size()) {
			path.clear();
			pathExists = true;
		}

		// Imprimir la fila de coordenadas del eje J
		System.out.print("   ");
		for(int j = 0; j < this.map[0].length; j++) {
			System.out.print(String.format("%2d ", j));
		}
		System.out.println();

		// Imprimir el laberinto junto con las coordenadas del eje I
		for(int i = 0; i < this.map.length; i++) {
		    System.out.print(String.format("%2d ", i));
		    for(int j = 0; j < this.map[i].length; j++) {
		        System.out.print(" " + this.map[i][j] + " ");
		    }
		    System.out.println();
		}

	} // cierre showMap()


	public void setEntranceExit() {
		// La siguiente condicion, si da true, se llamara al metodo que resetea el laberinto actual para
		// que no sea mostrado el anterior camino a la hora de introducir nuevas coordenadas para un
		// nuevo camino, en el mismo laberinto
		if(pathExists == true) {
			deleteOldPath();
			pathExists = false;
		}
		
		// Restriccion para que no se puedan introducir
		// coordenadas sin haber pasado por la opcion numero 1 de seleccionar el laberinto
		if(!this.loaded) {
			System.err.println("\nAntes de establecer las casillas de entrada y salida debes escoger un laberinto.");
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
			for(int j = 0; j < this.map[i].length; j++) {
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
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[i].length; j++) {
				// Si esta condicion se cumple se marcara la casilla de salida. Una vez cumplida se deja de iterar
				if (endI == i && endJ == j) {
					this.map[i][j] = 'S';
					break;
				}
			} // cierre bucle J (salida)
		} // cierre bucle I (salida)
			
		defineES = true;
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
	
	// Metodo que recorre la carpeta mazes y cada nombre de fichero que encuentre lo almacenara en un array de tipo File
	// para poder seleccionarlo y cargar posteriormente el laberinto correspondiente de cada fichero en el metodo loadMaze().
	// ES EL MISMO METODO QUE EL ANTERIOR PERO SIN TEXTO (System.out.print)
	private File[] showFilesInFolder2() {
		File folder = new File(Config.MAZES_PATH);
		
		if (folder.exists() && folder.isDirectory()) {
			File[] file = folder.listFiles();
			return file;
		} else {
			return null;
		}
		
	} // cierre showFilesInFolder()	
	
	
	// El siguiente metodo privado dimensiona el array Map antes de guardar el laberinto en el.
	private void arrayMapInit () {
	    try (BufferedReader file_length = new BufferedReader(new FileReader(Config.MAZES_PATH + this.filename))) {
	    	int rows_length = 0;
	    	int columns_length = 0;
	        String line;
	    	
	        while ((line = file_length.readLine()) != null) {
	        	
	        	for(int i = line.length()-1; i < line.length(); i++) {
	        		columns_length++;
	        	}
	        	
	        	rows_length++;
	        }
	        
	        // Inicializamos el array
	        this.map = new char[rows_length][columns_length];
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
	
	public void selectAlgorithm() {
		
		// Restriccion laberinto seleccionado
		if(!this.loaded) {
			System.err.println("\nAntes de buscar caminos, tienes que seleccionar un laberinto.");
			return;
		}
		
		// Restriccion para que se definan las casillas
		if(!this.defineES) {
			System.err.println("\nPara poder buscar caminos, primero tienes que definir las casillas de entrada y salida dentro del laberinto.");
			return;
		}
		
		// Menu para seleccionar caminos
		System.out.println(Config.MAZE_PATHS_MENU);
		
		int option = Interface.getInt("\nIntroduce opcion: ");
		switch(option) {
			case 1:
			counter_DFS++; // Se suma a uno para que comience en 0, y asi al entrar al metodo DFS, si no hay posibles soluciones del camino, se quedara en 0
			
			DFS(startI, startJ); // Llamada al metodo que ejecuta el algoritmo sobre el laberinto seleccionado
				// Si el counter_DFS del metodo DFS no ha sumado ninguno significa que no se ha encontrado solucion al camino en el laberinto
				if(counter_DFS == 0) {
					ErrorPathNotFound();// Si counter esta a 0, llamamos a este metodo que hace unos cambios para volver a iniciar coordenadas para otro posible camino
					return;
				} 
				
				if(counter_DFS != 0 && defineES == true) {
					System.out.println("\nCAMINO ENCONTRADO!!\n");
					defineES = false;
					showSteps();
					System.out.println();
					showMap();
					counter_DFS = -1;
					return;
				}
				
				break;
			case 2:
				/*
				 * Se ha intentado pero al final no ha salido.
				 * He intentado implementar otro algoritmo, en lugar de DFS, he intentado usar BFS (busqueda en anchura)
				 * que es similar al DFS pero intenta hallar el camino mas corto, he estado apunto de conseguirlo
				 * pero al final no me ha dado tiempo a mas. Aunque esta sea la entrega definitiva del proyecto
				 * Seguire intentando sacar el algoritmo para el camino mas corto...
				 */
				System.out.println("\nPor implementar...");
				break;
			case 0:
				System.out.println("\nSaliendo...");
				break;
		}
		
	} // cierre selectAlgorithm()
	
	// Algoritmo DFS (Busqueda en profundidad) --> Cualquier camino en el laberinto
	private boolean DFS(int row, int column) {
			
		// Si para cada llamada del metodo de forma recursiva, se cumple que la posicion de
		// I y J es igual al caracter 'S', el algoritmo termina
		if(this.map[row][column] == 'S') {
			return true;
		}
		
		// Si se cumple que row y column de la anterior llamada al este mismo metodo (vease Ej: result = DFS(row, column - 1))
		// es igual a '#' o a un asterisco el algoritmo termina devolviendo un false para que siga comprobando en el camino.
		// Cabe recordar que los asteriscos son caracteres pintados en el array map, que representan
		// que una casilla ya ha sido visitada. Sin embargo, al mostrar el mapa, se formatea cada asterisco dependiendo de la flecha con su direccion
		if(this.map[row][column] == '#' || this.map[row][column] == '*') {
			return false;
		}
		
		this.map[row][column] = '*'; // Se marca la casilla actual como visitada
		boolean result; // resultado de cada llamada recursiva a si mismo
		
		// Movimiento hacia la izquierda
		result = DFS(row, column - 1);
		
		//int counter_DFS = 0; // Comprueba si hay soluciones a la ruta entre 'E' y 'S'
		if(result) {
			path.add(new Coordinate(row, column-1, 1));
			counter_DFS++;
			return true;
		}
		
		// Movimiento hacia arriba
		result = DFS(row - 1, column);
		
		if(result) {
			path.add(new Coordinate(row-1, column, 2));
			counter_DFS++;
			return true;
		}
		
		// Movimiento hacia la derecha
		result = DFS(row, column + 1);
		
		if(result) {
			path.add(new Coordinate(row, column+1, 3));
			counter_DFS++;
			return true;
		}
		
		// Movimiento hacia abajo
		result = DFS(row + 1, column);
		
		if(result) {
			path.add(new Coordinate(row+1, column, 4));
			counter_DFS++;
			return true;
		}
		
		this.map[row][column] = ' '; // En caso de que ninguno entre en la condiciones, esa misma casilla se marcara en blanco
		
		return false; // Este false es como un rollback, si es el camino incorrecto, se vuelve hacia atras en las llamadas recursivas al metodo
		
	} // cierre DFS()
	
		
	private void ErrorPathNotFound() {
		
		System.err.println("\nNo se ha podido encontrar la ruta hasta la salida. "
				+ "\nDebes introducir nuevas coordenadas para la casilla de entrada y de salida."
				+ "\nO tambien puedes seleccionar otro laberinto.");
		defineES = false; // se convierte a false para que el usuario tenga que introducir nuevas
							// coordenadas de entrada y salida
		counter_DFS = -1; // Al ejecutarse este metodo, se devuelve el valor con el que se inicializo para
							// para que cuando se ejecute la siguiente vez dicho metodo
							// revise correctamente que se cumple la condicion
		System.out.print("\n");
		
	} // cierre ErrorPathNotFound()
	
	// Metodo que genera de nuevo el laberinto a la hora de seleccionar coordenadas nuevas
	// para que no muestre el anterior camino generado
	private void deleteOldPath() {
		
		File[] files = showFilesInFolder2();
		
		this.filename = files[numberMazeSelected-1].getName();
		
			try (BufferedReader file = new BufferedReader(new FileReader(Config.MAZES_PATH + this.filename))){
				arrayMapInit();
				String line;
				int i = 0;

				while ((line = file.readLine()) != null) {
					
					for (int j = 0; j < line.length(); j++) {
						this.map[i][j] = line.charAt(j);
					}
					i++;
				}

			} catch (FileNotFoundException e) {
				System.err.println("\nError: Fichero/laberinto no encontrado.");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("\nError mientras se accedia a la informacion del laberinto.");
				e.printStackTrace();
			}
			
	} // cierre deleteOldPath()
	
	// Metodo que muestra por consola los pasos que ha dado el algoritmo para encontrar la salida
	private void showSteps() {
		
		System.out.println(" Pasos --> " + (counter_DFS-1));
		String course = "";
		
		// En el bucle for empezamos desde el elemento ultimo añadido hasta el primer elemento para
		// que se muestre en el orden de menor numero de coordenada a mayor y hacia abajo
		for(int i = path.size() - 1; i >= 1; i--) {
			//Coordinate direc = path.get(i).direction;
			if(path.get(i).direction == 1) {
				course = "Izquierda";
			} else if(path.get(i).direction == 2) {
				course = "Arriba";
			} else if(path.get(i).direction == 3) {
				course = "Derecha";
			} else if(path.get(i).direction == 4) {
				course = "Abajo";
			}
			System.out.println("(" + path.get(i).i + "," + path.get(i).j  +") " + course );
		}
		
		
		
	} // cierre showSteps()

} // cierre class Maze