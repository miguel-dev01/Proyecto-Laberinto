package Classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
	
	public static boolean validateUsername(String username) {
		
		if(username.length() > 100) {
			return false;
		}
		
		String regex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$";
		return username.matches(regex);
		
	} // cierre validateUsername()
	
	public static boolean validatePassword(String pass) {
		
		if(pass.length() > 500) {
			return false;
		}
		
		String regex = "(?=.*[A-ZÑ])(?=.*[a-zñ])(?=.*\\d)(?=.+[$*-+&!?%]).{8,}";
		return pass.matches(regex);
		
		//return true;
	} // cierre validatePassword()
	
	public static boolean validateName(String name) {
		
		if(name.length() > 300) {
			return false;
		}
		
		// Expresion regular que valida si el nombre comienza
		// con una letra mayúscula, tiene al menos dos caracteres y contiene solo letras y espacios
		String regex = "^[A-Z][a-zA-Z ]{1,}$";
		return name.matches(regex);
		
	} // cierre validateName()
	
	public static boolean validateNif(String nif) {
	   
		// Comprobar si el NIF cumple con las siguientes condiciones:
	    // - Tiene una longitud de 9 caracteres
	    // - Los 8 primeros caracteres son números
	    // - El último caracter es una letra (mayúscula o minúscula)
	    String regex = "^[0-9]{8}[A-Za-z]$";
	    return nif.matches(regex);
	
	} // cierre validateNif()
	
	public static boolean validateEmail(String email) {
		
		if(email.length() > 300) {
			return false;
		}
		
		// Comprobar si el email cumple con las siguientes condiciones:
		// - Tiene al menos un carácter antes del símbolo "@"
		// - Tiene al menos un carácter entre el símbolo "@" y el símbolo "."
		// - Tiene al menos dos caracteres después del símbolo "."
		
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		return email.matches(regex);
	
	} // cierre validateEmail() 
	
	
	public static boolean validateDate(String fecha) {
	    
		String regex = "^(0?[1-9]|[1-2][0-9]|3[0-1])/(0?[1-9]|1[0-2])/\\d{4}$";
		
		return fecha.matches(regex);
	
	} // cierre validateDate()
	
	
	public static String encryptMd5(String password) {
	    try {
	        // Obtener una instancia del algoritmo de encriptación MD5
	        MessageDigest md = MessageDigest.getInstance("MD5");

	        // Convertir la contraseña a un arreglo de bytes y aplicar la encriptación MD5
	        byte[] messageDigest = md.digest(password.getBytes());

	        // Convertir el arreglo de bytes a una representación en hexadecimal
	        BigInteger no = new BigInteger(1, messageDigest);
	        String hashText = no.toString(16);

	        // Asegurarse de que la cadena de hash tenga 32 caracteres
	        while (hashText.length() < 32) {
	            hashText = "0" + hashText;
	        }

	        // Se devuelve la cadena de hash encriptada en MD5
	        return hashText;
	        
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    }
	
	} // cierre encryptMd5()
	
	// Metodo que devuelve fecha en formato europeo
	public static String formatDateEU(Date fecha) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    return sdf.format(fecha);
		
	} // cierre formatDateEU()
	
	// Metodo que devuelve la fecha en el formato del tipo de dato Date de SQL
    public static String formatDateSQL(String fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = null;
		try {
			date = format.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        format.applyPattern("yyyy-MM-dd");
        return format.format(date);
    }
	
    // Metodo que devuelve la edad calculado en base a una fecha
    public static int getAge(String date) {
    	String[] dateinArray = date.split("/");
    	String anyio_date = dateinArray[2];
    	
    	Calendar calendar = Calendar.getInstance();
        int anyio_actual = calendar.get(Calendar.YEAR);
    	
    	return anyio_actual - Integer.parseInt(anyio_date);
    } // cierre getAge()
    
	
	
} // cierre class Utils