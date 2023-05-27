package Classes;
/**
 *  Clase que crea el fichero de texto plano syslog.txt (si no existe) 
 *  y guardara los eventos en ese fichero
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
	/**Tener en cuenta que la ruta del fichero puede variar segun el SO en el que nos encontremos.
	 * Aunque en la mayoria de situaciones, no haria falta realizar muchas modificaciones ya que
	 * al usarse una ruta relativa, no deberia importar que sistema de ficheros de SO se este usando.
	 * Es importante que ya este creado la carpeta assets/files de esa forma, para que se guarde correctamente.
	 * La variable 'currentDirectory' obtendra el directorio actual y se lo pasara al objeto de la clase File.
	 * Dicha clase tomara esa referencia como directorio actual del proyecto, y creara a traves 'relativePath' 
	 * el fichero en cuestion.
	*/
	private static String currentDirectory = System.getProperty("user.dir");
	private static String relativePath = "assets/files/";
	private static String fileName = "syslog.txt";

	private static File file = new File(currentDirectory, relativePath + fileName);
	
	public static void createFile() {
		// Condicion para verificar si el archivo existe
		if(file.isFile()) {
			return;
			/**
			 *  Si existe el fichero, se cumplira la condicion y el metodo terminara.
			 *  Sino, se continuara al try-catch creando el fichero que no existe.
			 */
		}
		
		try {
			// A partir del objeto File creamos el fichero
			if (file.createNewFile()) {
				System.out.println("El fichero se ha creado correctamente!");
			} else {
				System.err.println("No ha podido ser creado el fichero " + fileName + ".txt");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	} // cierre createFile()

	/**
	 * Metodo estatico que guarda los eventos en el fichero syslog.txt
	 */
	public static void loggerEvents(String eventType, String eventData) {
		// Formato: fecha y hora - tipo de evento - datos del evento
		String result = getCurrentDate() + " - " + eventType + " - " + eventData;
		
		/**
		 * Indicamos la ruta relativa, donde se encuentra nuestro fichero syslog para escribir lo que
		 * vamos realizando sobre el programa. El resultado de esa ruta relativa se guardara en una
		 * variable (filePath) y se la pasaremos a un objeto de la clase FileWriter, que sera la clase
		 * especializada en escribir sobre el fichero de texto lo que le pasemos con la variable 'result'
		 */
		String currentDirectory = System.getProperty("user.dir");
		String relativePath = "assets/files/syslog.txt";
		String filePath = currentDirectory + "/" + relativePath;
		
		try {
			FileWriter fileWriter = new FileWriter(filePath, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(result);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // cierre loggerEvents()
	
	/**
	 * Metodo que, al llamarlo, devolvera la fecha y hora actual en el momento de su llamada
	 */
	public static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat formatCurrentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		return formatCurrentDate.format(calendar.getTime());
		
	} // cierre getCurrentDate()

} // cierre class