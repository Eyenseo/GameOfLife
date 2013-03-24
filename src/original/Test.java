package original;

/**
 * Created with IntelliJ IDEA.
 * User: Eyenseo
 * Date: 13.03.13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class Test {
	public static void main(String[] args) {
		for(int i = 0; i < 38000; i++) {
			if((char) i == '═' || (char) i == '║' || (char) i == '╔' || (char) i == '╗' || (char) i == '╚' ||
			   (char) i == '╝' ||  (char) i == 'ä'){
				System.out.print("" + i + ".\t" + (char) i + "\n");
			}
		}
	}
}
