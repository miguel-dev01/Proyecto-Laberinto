package Classes;
// Clase que crea el fichero de texto plano syslog.txt (si no existe) 
// y guardara los eventos en ese fichero

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
	//**Tener en cuenta que la ruta del fichero puede variar segun el SO en el que nos encontremos
	private static String route = "/home/miguel/eclipse-workspace/Proyecto1.2.0/assets/files";
	private static String fileName = "syslog.txt";
	
	private static File file = new File(route, fileName);
	
	public static void createFile() {
		// Condicion para verificar si el archivo existe
		if(file.isFile()) {
			return;
			// Si existe el fichero, devolvera true y el metodo terminara
		}
		
		try {
			// A partir del objeto File creamos el fichero
			if (file.createNewFile()) {
				System.out.println("El fichero se ha creado correctamente!");
			} else {
				System.err.println("No ha podido ser creado el fichero syslog.txt");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	} // cierre createFile()

	/*
	 * Metodo estatico que guarda los eventos en el fichero syslog.txt
	 */
	public static void loggerEvents(String eventType, String eventData) {
		// Formato: fecha y hora - tipo de evento - datos del evento
		String result = getCurrentDate() + " - " + eventType + " - " + eventData;
		
		try {
			FileWriter fileWriter = new FileWriter("syslog.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(result);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // cierre loggerEvents()
	
	/*
	 * Metodo que, al llamar a este, devolvera la fecha y hora actual en el momento de su llamada
	 */
	public static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatCurrentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        return formatCurrentDate.format(calendar.getTime());
		
	} // cierre getCurrentDate()

} // cierre class