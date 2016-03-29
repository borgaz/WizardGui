import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class Constants {
	public MaskFormatter postcodeMask;
	public MaskFormatter phoneMask;
	public static String firstNameRegex = "[^a-zA-ZąćęłńóśżźĄĆĘŁŃÓŚŻŹ]+";
	public static String lastNameRegex = "[^a-zA-Z-ąćęłńóśżźĄĆĘŁŃÓŚŻŹ]+";
	public static String numberRegex = "[^\\d]+";
	public static String cityStreetRegex = "[^a-zA-Z-\\.ąćęłńóśżźĄĆĘŁŃÓŚŻŹ\\s]+";
	public static String firstNameErrorText = "Imię musi być conajmniej dwuliterowe!";
	public static String lastNameErrorText = "Nazwisko musi być conajmniej dwuliterowe!";
	public static String mainErrorText = "Nie wszystkie pola zostały wypełnione poprawnie!";
	
	public Constants() throws ParseException {
	postcodeMask = new MaskFormatter("##-###");
	phoneMask = new MaskFormatter("###-###-###");
	}
}
