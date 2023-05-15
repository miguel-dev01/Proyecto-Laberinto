package Classes;

public class Coordinate {
	
	public int i;
	public int j;
	public int direction;
	
	public Coordinate () {} // Constructor vacio
	
	// Constructor para usar en los casos donde no sea necesario usar la direccion y tan solo las coordenadas
	public Coordinate(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	// Constructor clase Coordinate
	public Coordinate(int i, int j, int direction) { 
		
		this.i = i;
		this.j = j;
		this.direction = direction;
		
	}
	
	// Algunos metodos adicionales
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}
	
} // cierre Clase Coordinate