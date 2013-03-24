package original; /**
 * macht das Einlesen von Einfachen Daten einfacher 
 * durch Verdecken des Exception-Handlings
*/


import java.io.BufferedReader;
import java.io.InputStreamReader;

class IO {

	private static BufferedReader input
			                              = new BufferedReader(new InputStreamReader(System.in));
	private static String         eingabe = "";

	// Einlesen eines char
	public static char readChar() {
		try {
			eingabe = input.readLine();
			return eingabe.charAt(0);
		} catch(Exception e) {
			return '\0';
		}
	}

	// Einlesen eines int
	public static int readInt() {
		try {
			eingabe = input.readLine();
			Integer string_to_int = new Integer(eingabe);
			return string_to_int;
		}
		catch (Exception e) {
		  return 0;
		}
	}

	// Ausgabe
	public static void println(String s){
		System.out.println(s);
	}

	// Ausgabe erweitert von Roland Jaeger
	public static void nlprint(String s){
		System.out.println("\n"+s);
	}

}

